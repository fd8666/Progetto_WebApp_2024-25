--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

-- Started on 2025-02-06 22:46:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE cardswap;
--
-- TOC entry 5013 (class 1262 OID 16410)
-- Name: cardswap; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE cardswap WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United Kingdom.1252';


\connect cardswap

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 5015 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 910 (class 1247 OID 24673)
-- Name: status_type; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public.status_type AS ENUM (
    'sent',
    'refused',
    'accepted'
);


--
-- TOC entry 244 (class 1255 OID 24722)
-- Name: check_offers_limit(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.check_offers_limit() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF (SELECT COUNT(*) FROM offers WHERE trade = NEW.trade) >= 5 THEN
        RAISE EXCEPTION 'A trade can have at most 5 offers.';
    END IF;

    RETURN NEW;
END;
$$;


--
-- TOC entry 243 (class 1255 OID 24721)
-- Name: check_requests_limit(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.check_requests_limit() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF (SELECT COUNT(*) FROM requests WHERE trade = NEW.trade) >= 5 THEN
        RAISE EXCEPTION 'A trade can have at most 5 requests.';
    END IF;

    RETURN NEW;
END;
$$;


--
-- TOC entry 256 (class 1255 OID 24736)
-- Name: process_trade(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.process_trade() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.status = 'accepted' THEN
        UPDATE inventories
        SET amount = amount - 1
        FROM offers o
        WHERE inventories.user = NEW.from
          AND inventories.card = o.card
          AND o.trade = NEW.id;

        UPDATE inventories
        SET amount = amount - 1
        FROM requests r
        WHERE inventories.user = NEW.to
          AND inventories.card = r.card
          AND r.trade = NEW.id;

        INSERT INTO inventories ("user", card, amount)
        SELECT NEW.to, o.card, 1
        FROM offers o
        WHERE o.trade = NEW.id
        ON CONFLICT (user, card)
        DO UPDATE SET amount = inventories.amount + 1;

        INSERT INTO inventories ("user", card, amount)
        SELECT NEW.from, r.card, 1
        FROM requests r
        WHERE r.trade = NEW.id
        ON CONFLICT (user, card)
        DO UPDATE SET amount = inventories.amount + 1;
    END IF;

    RETURN NEW;
END;
$$;


--
-- TOC entry 231 (class 1259 OID 16517)
-- Name: card_tags_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.card_tags_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 230 (class 1259 OID 16502)
-- Name: card_tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.card_tags (
    id integer DEFAULT nextval('public.card_tags_primary_key'::regclass) NOT NULL,
    card integer NOT NULL,
    tag integer NOT NULL
);


--
-- TOC entry 225 (class 1259 OID 16482)
-- Name: cards_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.cards_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 224 (class 1259 OID 16475)
-- Name: cards; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cards (
    id integer DEFAULT nextval('public.cards_primary_key'::regclass) NOT NULL,
    name character varying NOT NULL,
    game integer NOT NULL,
    expansion integer,
    identifier character varying,
    description character varying
);


--
-- TOC entry 232 (class 1259 OID 16544)
-- Name: inventories_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.inventories_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 234 (class 1259 OID 16546)
-- Name: inventories; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.inventories (
    id integer DEFAULT nextval('public.inventories_primary_key'::regclass) NOT NULL,
    "user" integer NOT NULL,
    card integer NOT NULL,
    amount integer DEFAULT 1 NOT NULL
);


--
-- TOC entry 241 (class 1259 OID 24727)
-- Name: inventories_with_positive_amount; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW public.inventories_with_positive_amount AS
 SELECT id,
    "user",
    card,
    amount
   FROM public.inventories
  WHERE (amount > 0);


--
-- TOC entry 242 (class 1259 OID 24731)
-- Name: cards_with_positive_amount; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW public.cards_with_positive_amount AS
 SELECT DISTINCT c.id,
    c.name,
    c.game,
    c.expansion,
    c.identifier,
    c.description
   FROM (public.cards c
     JOIN public.inventories_with_positive_amount i ON ((c.id = i.card)));


--
-- TOC entry 227 (class 1259 OID 16484)
-- Name: expansions_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.expansions_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 229 (class 1259 OID 16494)
-- Name: expansions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.expansions (
    id integer DEFAULT nextval('public.expansions_primary_key'::regclass) NOT NULL,
    game integer NOT NULL,
    name character varying,
    description character varying
);


--
-- TOC entry 226 (class 1259 OID 16483)
-- Name: games_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.games_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 228 (class 1259 OID 16486)
-- Name: games; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.games (
    id integer DEFAULT nextval('public.games_primary_key'::regclass) NOT NULL,
    name character varying,
    description character varying
);


--
-- TOC entry 237 (class 1259 OID 24658)
-- Name: offers_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.offers_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 240 (class 1259 OID 24666)
-- Name: offers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.offers (
    id integer DEFAULT nextval('public.offers_primary_key'::regclass) NOT NULL,
    trade integer NOT NULL,
    card integer NOT NULL
);


--
-- TOC entry 220 (class 1259 OID 16445)
-- Name: sessions_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.sessions_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 218 (class 1259 OID 16422)
-- Name: sessions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sessions (
    id integer DEFAULT nextval('public.sessions_primary_key'::regclass) NOT NULL,
    user_id integer,
    ipv4 character(15),
    cookie character varying NOT NULL,
    user_agent character varying,
    creation_date date DEFAULT now() NOT NULL,
    time_to_live integer DEFAULT 604800 NOT NULL,
    valid boolean DEFAULT true NOT NULL,
    private_key character varying
);


--
-- TOC entry 5034 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN sessions.time_to_live; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.sessions.time_to_live IS '1 week (604800 seconds)';


--
-- TOC entry 221 (class 1259 OID 16460)
-- Name: valid_sessions; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW public.valid_sessions AS
 SELECT id,
    user_id,
    ipv4,
    cookie,
    user_agent,
    creation_date,
    time_to_live,
    valid,
    private_key
   FROM public.sessions s
  WHERE ((valid = true) AND ((creation_date + ((time_to_live)::double precision * '00:00:01'::interval)) > now()));


--
-- TOC entry 236 (class 1259 OID 16559)
-- Name: registration_sessions; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW public.registration_sessions AS
 SELECT id,
    user_id,
    ipv4,
    cookie,
    user_agent,
    creation_date,
    time_to_live,
    valid
   FROM public.valid_sessions
  WHERE (user_id IS NULL);


--
-- TOC entry 238 (class 1259 OID 24659)
-- Name: requests_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.requests_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 239 (class 1259 OID 24660)
-- Name: requests; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.requests (
    id integer DEFAULT nextval('public.requests_primary_key'::regclass) NOT NULL,
    trade integer NOT NULL,
    card integer NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 16466)
-- Name: tags_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tags_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 223 (class 1259 OID 16467)
-- Name: tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tags (
    id integer DEFAULT nextval('public.tags_primary_key'::regclass) NOT NULL,
    name character varying NOT NULL
);


--
-- TOC entry 233 (class 1259 OID 16545)
-- Name: trades_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.trades_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 235 (class 1259 OID 16552)
-- Name: trades; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.trades (
    id integer DEFAULT nextval('public.trades_primary_key'::regclass) NOT NULL,
    "from" integer NOT NULL,
    "to" integer NOT NULL,
    status public.status_type DEFAULT 'sent'::public.status_type NOT NULL,
    message character varying
);


--
-- TOC entry 219 (class 1259 OID 16443)
-- Name: users_primary_key; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.users_primary_key
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 217 (class 1259 OID 16417)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id integer DEFAULT nextval('public.users_primary_key'::regclass) NOT NULL,
    username character varying NOT NULL,
    email character varying NOT NULL,
    password_hash character varying,
    google_id character varying,
    creation_date date DEFAULT now() NOT NULL,
    biography character varying,
    CONSTRAINT users_check CHECK (((password_hash IS NOT NULL) OR (google_id IS NOT NULL)))
);


--
-- TOC entry 4998 (class 0 OID 16502)
-- Dependencies: 230
-- Data for Name: card_tags; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4992 (class 0 OID 16475)
-- Dependencies: 224
-- Data for Name: cards; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4997 (class 0 OID 16494)
-- Dependencies: 229
-- Data for Name: expansions; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4996 (class 0 OID 16486)
-- Dependencies: 228
-- Data for Name: games; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 5002 (class 0 OID 16546)
-- Dependencies: 234
-- Data for Name: inventories; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 5007 (class 0 OID 24666)
-- Dependencies: 240
-- Data for Name: offers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 5006 (class 0 OID 24660)
-- Dependencies: 239
-- Data for Name: requests; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4987 (class 0 OID 16422)
-- Dependencies: 218
-- Data for Name: sessions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sessions VALUES (1, 20, '1              ', '1', '1', '2025-01-26', 604800, true, NULL);
INSERT INTO public.sessions VALUES (2, 21, NULL, '345e0b71-61d6-4f43-b89a-ceb5b4583f45', NULL, '2025-02-03', 604800, true, NULL);
INSERT INTO public.sessions VALUES (3, 21, NULL, 'd6b05c4c-599e-49d6-bb57-cb1a5a465093', NULL, '2025-02-03', 604800, true, NULL);
INSERT INTO public.sessions VALUES (4, 21, NULL, 'c327c078-498f-4f9c-8171-ee9e78f1dcc0', NULL, '2025-02-03', 604800, true, NULL);
INSERT INTO public.sessions VALUES (5, 21, NULL, '4d8bd6bc-6550-475a-9330-22a57bc88773', NULL, '2025-02-03', 604800, true, NULL);
INSERT INTO public.sessions VALUES (6, 21, NULL, 'eb7b67aa-6424-414b-9043-6de3ba25cc90', NULL, '2025-02-03', 604800, false, NULL);
INSERT INTO public.sessions VALUES (7, 21, NULL, '736e15e8-1aa6-45f0-a788-ce2d456e98a7', NULL, '2025-02-03', 604800, false, NULL);
INSERT INTO public.sessions VALUES (8, 21, NULL, '82181b92-485f-4a21-9289-a92666ddac27', NULL, '2025-02-03', 604800, true, NULL);
INSERT INTO public.sessions VALUES (9, 21, NULL, 'cb240eda-4fde-4fda-ac9f-2b359fad65dc', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (10, 21, NULL, '78d6df1c-23dc-4582-b24d-f585deee6cfd', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (11, 21, NULL, '75663d4f-ef2e-446e-9f65-baabbcd483be', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (12, 21, NULL, '606bee25-32f1-4937-a319-b0cf6534be24', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (13, 21, NULL, '7d5e477b-8123-43d9-a563-1ab53f604454', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (14, 21, NULL, '2f8028da-66f1-46f8-a76b-fea52120006f', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (15, 21, NULL, '21c0cd2d-855b-4dc7-b36b-4ac87eb9eb14', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (16, 21, NULL, '7bb6a7fa-135d-45a7-9670-fe908ae6ca1d', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (17, 21, NULL, 'b8707fc5-798b-4cea-b525-60b8e0e9ac82', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (18, 21, NULL, '4d2062c9-1189-4ee8-9503-eb226bf74375', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (19, 21, NULL, '44dcf751-83e5-453b-b736-c99604282eb1', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (20, 21, NULL, 'affd6587-ed73-4600-913c-7b67e5320470', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (21, 21, NULL, 'ae986768-3dc5-443c-bc11-eca027226826', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (22, 21, NULL, 'ad20b258-26cd-4d81-a694-67a3fec64ccf', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (23, 21, NULL, '9ef1db06-a3ad-4b93-848b-4033df3bc44e', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (24, 21, NULL, '904cdfaa-c22e-4843-8912-58537e460e98', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (25, 21, NULL, '66224e16-6223-47f9-a735-c44feabfd394', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (26, 21, NULL, '56f7ce10-0a1d-4ecb-9c4f-ce1f44875c89', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (27, 21, NULL, '1089d39b-037c-4720-b8db-beb957548a80', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (28, 21, NULL, 'ac864525-4e3a-4672-9c20-0e426d656b77', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (29, 21, NULL, '9a492450-4b7d-49a5-ab3a-4faf887ef55e', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (30, 21, NULL, 'ff6304ba-30ac-4b40-9058-7c9236c1af2f', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (31, 21, NULL, 'e7c93b81-2b3b-4bdd-bc0c-483f306320ee', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (32, 21, NULL, '2fc96b8f-4ac8-4c66-80bb-220c45bc5397', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (33, 21, NULL, '685a63f6-fdfb-485a-a0a2-405c2acb4046', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (34, 21, NULL, 'f7d31105-0fb6-4429-939d-a3e4a10a8f2d', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (35, 21, NULL, '76b2f7bb-2e21-4048-aefa-c6cc0f7caabb', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (36, 21, NULL, 'f981edb5-8b39-41ca-b532-cb48353365b1', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (37, 21, NULL, 'a68e2368-204e-4fe2-8f96-2737e93fb895', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (38, 21, NULL, 'd0294b74-a094-4d09-a9c5-be693cb81781', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (39, 21, NULL, 'efd79053-93b4-4ee5-88a3-568a4fb8901d', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (40, 21, NULL, '85d88f32-ffe9-407b-a724-21629502f9f1', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (41, 21, NULL, 'acee8cc4-846d-4f5d-b3ff-a1ff4d6e6e4a', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (42, 21, NULL, 'cb46b81a-1405-483b-a45a-bcff138026cd', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (43, 21, NULL, '2e1de75a-f590-40ac-bd3b-cfe8c66caa75', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (44, 21, NULL, '0e797999-67a1-444a-bbd0-18150d3763c0', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (45, 21, NULL, 'd860da09-cd83-4d6d-b079-de3458596cf4', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (46, 21, NULL, 'f5baa422-ebbf-4322-b4d7-a53b1e89b86a', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (47, 21, NULL, 'ce87b537-fa9c-4296-9449-e5aa37166f84', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (48, 21, NULL, '4569fcc9-cc99-4568-9ee1-51fbd60bb4c4', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (49, 21, NULL, 'edad40fd-d3c9-4a8d-a263-d3569a983d2f', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (50, 21, NULL, 'cfc39619-669d-4f5c-b0de-fff43d53fb44', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (51, 21, NULL, 'e1376c05-8828-46cd-8318-cac971837007', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (52, 21, NULL, '61ad6331-f2a3-4e79-b242-40ae1c6833ef', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (53, 21, NULL, '58c2aef5-f99f-45c0-830e-dee448817087', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (54, 21, NULL, '2052eb3a-8e4b-489e-8496-f02576e62dfc', NULL, '2025-02-05', 604800, true, NULL);
INSERT INTO public.sessions VALUES (55, 21, NULL, 'c41c6074-8eba-46c3-bfc3-20da19cbe053', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (56, 21, NULL, 'fc86dd2d-9433-4a87-b8fc-284f2d2181ab', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (57, 21, NULL, 'dda4e41e-566d-4ad3-9c5f-50cbdcbf9807', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (58, 21, NULL, 'e6acf8b9-bca9-4b10-beaa-98acdc11be42', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (59, 21, NULL, 'b741f8b4-a44b-4193-80ab-be6f51b5b9a9', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (60, 21, NULL, 'c1fa3a58-a725-418b-902d-1cf494a43461', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (61, 21, NULL, '1f192c2f-fde3-4c7f-9b6f-ee51c22497bb', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (62, 21, NULL, '73642416-0b54-473c-93e2-6ea3fae9c3ef', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (63, 21, NULL, '5655c461-f14e-4f80-be10-ffd644790250', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (64, 21, NULL, '14b35a20-b33a-4ec5-9ecc-0e69dd8c44a1', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (65, 21, NULL, '6ba328b3-2ec6-4936-8fa5-244b0cf4cd25', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (66, 21, NULL, 'd4ad0fae-8fb8-47cf-9a73-8e2427986fd8', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (67, 21, NULL, 'da027727-40e7-4b53-9d3c-7246177acda3', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (68, 21, NULL, 'ba490e6b-796e-4521-a1e2-afdfd690b1be', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (69, 21, NULL, 'ab7bc5e8-507a-46ed-9bb2-45bb6b660d27', NULL, '2025-02-06', 604800, true, NULL);
INSERT INTO public.sessions VALUES (70, 27, NULL, 'ea0750ec-415f-4b9f-aa3d-406c78faaaae', NULL, '2025-02-06', 604800, true, NULL);


--
-- TOC entry 4991 (class 0 OID 16467)
-- Dependencies: 223
-- Data for Name: tags; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 5003 (class 0 OID 16552)
-- Dependencies: 235
-- Data for Name: trades; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4986 (class 0 OID 16417)
-- Dependencies: 217
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.users VALUES (20, 'test', 's', 's', NULL, '2025-01-26', NULL);
INSERT INTO public.users VALUES (21, 'ciao2', 'test@gmail.com', 'Test1234', NULL, '2025-02-03', NULL);
INSERT INTO public.users VALUES (26, 'testo', 'testo@gmail.com', 'Testo1234', NULL, '2025-02-06', NULL);
INSERT INTO public.users VALUES (27, 'testo12', 'testo12@gmail.com', 'Testo1234', NULL, '2025-02-06', NULL);


--
-- TOC entry 5046 (class 0 OID 0)
-- Dependencies: 231
-- Name: card_tags_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.card_tags_primary_key', 1, false);


--
-- TOC entry 5047 (class 0 OID 0)
-- Dependencies: 225
-- Name: cards_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.cards_primary_key', 1, false);


--
-- TOC entry 5048 (class 0 OID 0)
-- Dependencies: 227
-- Name: expansions_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.expansions_primary_key', 1, false);


--
-- TOC entry 5049 (class 0 OID 0)
-- Dependencies: 226
-- Name: games_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.games_primary_key', 1, false);


--
-- TOC entry 5050 (class 0 OID 0)
-- Dependencies: 232
-- Name: inventories_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.inventories_primary_key', 1, false);


--
-- TOC entry 5051 (class 0 OID 0)
-- Dependencies: 237
-- Name: offers_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.offers_primary_key', 1, false);


--
-- TOC entry 5052 (class 0 OID 0)
-- Dependencies: 238
-- Name: requests_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.requests_primary_key', 1, false);


--
-- TOC entry 5053 (class 0 OID 0)
-- Dependencies: 220
-- Name: sessions_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sessions_primary_key', 70, true);


--
-- TOC entry 5054 (class 0 OID 0)
-- Dependencies: 222
-- Name: tags_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tags_primary_key', 1, false);


--
-- TOC entry 5055 (class 0 OID 0)
-- Dependencies: 233
-- Name: trades_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.trades_primary_key', 1, false);


--
-- TOC entry 5056 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_primary_key; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.users_primary_key', 27, true);


--
-- TOC entry 4811 (class 2606 OID 16506)
-- Name: card_tags card_tags_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card_tags
    ADD CONSTRAINT card_tags_pk PRIMARY KEY (id);


--
-- TOC entry 4797 (class 2606 OID 16481)
-- Name: cards cards_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cards
    ADD CONSTRAINT cards_pk PRIMARY KEY (id);


--
-- TOC entry 4799 (class 2606 OID 16541)
-- Name: cards cards_unique; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cards
    ADD CONSTRAINT cards_unique UNIQUE (name);


--
-- TOC entry 4801 (class 2606 OID 16543)
-- Name: cards cards_unique_1; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cards
    ADD CONSTRAINT cards_unique_1 UNIQUE (identifier, game);


--
-- TOC entry 4807 (class 2606 OID 16501)
-- Name: expansions expansions_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.expansions
    ADD CONSTRAINT expansions_pk PRIMARY KEY (id);


--
-- TOC entry 4809 (class 2606 OID 16539)
-- Name: expansions expansions_unique; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.expansions
    ADD CONSTRAINT expansions_unique UNIQUE (name);


--
-- TOC entry 4803 (class 2606 OID 16493)
-- Name: games games_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_pk PRIMARY KEY (id);


--
-- TOC entry 4805 (class 2606 OID 16537)
-- Name: games games_unique; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_unique UNIQUE (name);


--
-- TOC entry 4813 (class 2606 OID 16551)
-- Name: inventories inventory_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventories
    ADD CONSTRAINT inventory_pk PRIMARY KEY (id);


--
-- TOC entry 4819 (class 2606 OID 24671)
-- Name: offers offers_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.offers
    ADD CONSTRAINT offers_pk PRIMARY KEY (id);


--
-- TOC entry 4817 (class 2606 OID 24665)
-- Name: requests requests_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.requests
    ADD CONSTRAINT requests_pk PRIMARY KEY (id);


--
-- TOC entry 4791 (class 2606 OID 16426)
-- Name: sessions sessions_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_pk PRIMARY KEY (id);


--
-- TOC entry 4793 (class 2606 OID 16474)
-- Name: tags tags_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT tags_pk PRIMARY KEY (id);


--
-- TOC entry 4795 (class 2606 OID 16535)
-- Name: tags tags_unique; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT tags_unique UNIQUE (name);


--
-- TOC entry 4815 (class 2606 OID 16556)
-- Name: trades trades_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT trades_pk PRIMARY KEY (id);


--
-- TOC entry 4789 (class 2606 OID 16421)
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 4836 (class 2620 OID 24724)
-- Name: offers check_offers_limit_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER check_offers_limit_trigger BEFORE INSERT ON public.offers FOR EACH ROW EXECUTE FUNCTION public.check_offers_limit();


--
-- TOC entry 4835 (class 2620 OID 24723)
-- Name: requests check_requests_limit_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER check_requests_limit_trigger BEFORE INSERT ON public.requests FOR EACH ROW EXECUTE FUNCTION public.check_requests_limit();


--
-- TOC entry 4834 (class 2620 OID 24737)
-- Name: trades trade_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER trade_trigger AFTER UPDATE OF status ON public.trades FOR EACH ROW WHEN ((new.status = 'accepted'::public.status_type)) EXECUTE FUNCTION public.process_trade();


--
-- TOC entry 4824 (class 2606 OID 16507)
-- Name: card_tags card_tags_cards_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card_tags
    ADD CONSTRAINT card_tags_cards_fk FOREIGN KEY (card) REFERENCES public.cards(id);


--
-- TOC entry 4825 (class 2606 OID 16512)
-- Name: card_tags card_tags_tags_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card_tags
    ADD CONSTRAINT card_tags_tags_fk FOREIGN KEY (tag) REFERENCES public.tags(id);


--
-- TOC entry 4821 (class 2606 OID 16524)
-- Name: cards cards_expansions_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cards
    ADD CONSTRAINT cards_expansions_fk FOREIGN KEY (expansion) REFERENCES public.expansions(id);


--
-- TOC entry 4822 (class 2606 OID 16519)
-- Name: cards cards_games_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cards
    ADD CONSTRAINT cards_games_fk FOREIGN KEY (game) REFERENCES public.games(id);


--
-- TOC entry 4823 (class 2606 OID 16529)
-- Name: expansions expansions_games_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.expansions
    ADD CONSTRAINT expansions_games_fk FOREIGN KEY (game) REFERENCES public.games(id);


--
-- TOC entry 4826 (class 2606 OID 24711)
-- Name: inventories inventories_cards_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventories
    ADD CONSTRAINT inventories_cards_fk FOREIGN KEY (card) REFERENCES public.cards(id);


--
-- TOC entry 4827 (class 2606 OID 24716)
-- Name: inventories inventories_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventories
    ADD CONSTRAINT inventories_users_fk FOREIGN KEY ("user") REFERENCES public.users(id);


--
-- TOC entry 4832 (class 2606 OID 24695)
-- Name: offers offers_cards_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.offers
    ADD CONSTRAINT offers_cards_fk FOREIGN KEY (card) REFERENCES public.cards(id);


--
-- TOC entry 4833 (class 2606 OID 24690)
-- Name: offers offers_trades_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.offers
    ADD CONSTRAINT offers_trades_fk FOREIGN KEY (trade) REFERENCES public.trades(id);


--
-- TOC entry 4830 (class 2606 OID 24705)
-- Name: requests requests_cards_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.requests
    ADD CONSTRAINT requests_cards_fk FOREIGN KEY (card) REFERENCES public.cards(id);


--
-- TOC entry 4831 (class 2606 OID 24700)
-- Name: requests requests_trades_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.requests
    ADD CONSTRAINT requests_trades_fk FOREIGN KEY (trade) REFERENCES public.trades(id);


--
-- TOC entry 4820 (class 2606 OID 16427)
-- Name: sessions sessions_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_users_fk FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4828 (class 2606 OID 24680)
-- Name: trades trades_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT trades_users_fk FOREIGN KEY ("from") REFERENCES public.users(id);


--
-- TOC entry 4829 (class 2606 OID 24685)
-- Name: trades trades_users_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT trades_users_fk_1 FOREIGN KEY ("to") REFERENCES public.users(id);


--
-- TOC entry 5014 (class 0 OID 0)
-- Dependencies: 5013
-- Name: DATABASE cardswap; Type: ACL; Schema: -; Owner: -
--

REVOKE CONNECT,TEMPORARY ON DATABASE cardswap FROM PUBLIC;


--
-- TOC entry 5016 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: -
--

GRANT ALL ON SCHEMA public TO cardswap;


--
-- TOC entry 5017 (class 0 OID 0)
-- Dependencies: 244
-- Name: FUNCTION check_offers_limit(); Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON FUNCTION public.check_offers_limit() TO cardswap;


--
-- TOC entry 5018 (class 0 OID 0)
-- Dependencies: 243
-- Name: FUNCTION check_requests_limit(); Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON FUNCTION public.check_requests_limit() TO cardswap;


--
-- TOC entry 5019 (class 0 OID 0)
-- Dependencies: 256
-- Name: FUNCTION process_trade(); Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON FUNCTION public.process_trade() TO cardswap;


--
-- TOC entry 5020 (class 0 OID 0)
-- Dependencies: 231
-- Name: SEQUENCE card_tags_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.card_tags_primary_key TO cardswap;


--
-- TOC entry 5021 (class 0 OID 0)
-- Dependencies: 225
-- Name: SEQUENCE cards_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.cards_primary_key TO cardswap;


--
-- TOC entry 5022 (class 0 OID 0)
-- Dependencies: 224
-- Name: TABLE cards; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.cards TO cardswap;


--
-- TOC entry 5023 (class 0 OID 0)
-- Dependencies: 232
-- Name: SEQUENCE inventories_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.inventories_primary_key TO cardswap;


--
-- TOC entry 5024 (class 0 OID 0)
-- Dependencies: 234
-- Name: TABLE inventories; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.inventories TO cardswap;


--
-- TOC entry 5025 (class 0 OID 0)
-- Dependencies: 241
-- Name: TABLE inventories_with_positive_amount; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.inventories_with_positive_amount TO cardswap;


--
-- TOC entry 5026 (class 0 OID 0)
-- Dependencies: 242
-- Name: TABLE cards_with_positive_amount; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.cards_with_positive_amount TO cardswap;


--
-- TOC entry 5027 (class 0 OID 0)
-- Dependencies: 227
-- Name: SEQUENCE expansions_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.expansions_primary_key TO cardswap;


--
-- TOC entry 5028 (class 0 OID 0)
-- Dependencies: 229
-- Name: TABLE expansions; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.expansions TO cardswap;


--
-- TOC entry 5029 (class 0 OID 0)
-- Dependencies: 226
-- Name: SEQUENCE games_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.games_primary_key TO cardswap;


--
-- TOC entry 5030 (class 0 OID 0)
-- Dependencies: 228
-- Name: TABLE games; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.games TO cardswap;


--
-- TOC entry 5031 (class 0 OID 0)
-- Dependencies: 237
-- Name: SEQUENCE offers_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.offers_primary_key TO cardswap;


--
-- TOC entry 5032 (class 0 OID 0)
-- Dependencies: 240
-- Name: TABLE offers; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.offers TO cardswap;


--
-- TOC entry 5033 (class 0 OID 0)
-- Dependencies: 220
-- Name: SEQUENCE sessions_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.sessions_primary_key TO cardswap;


--
-- TOC entry 5035 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE sessions; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.sessions TO cardswap;


--
-- TOC entry 5036 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE valid_sessions; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.valid_sessions TO cardswap;


--
-- TOC entry 5037 (class 0 OID 0)
-- Dependencies: 236
-- Name: TABLE registration_sessions; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.registration_sessions TO cardswap;


--
-- TOC entry 5038 (class 0 OID 0)
-- Dependencies: 238
-- Name: SEQUENCE requests_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.requests_primary_key TO cardswap;


--
-- TOC entry 5039 (class 0 OID 0)
-- Dependencies: 239
-- Name: TABLE requests; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.requests TO cardswap;


--
-- TOC entry 5040 (class 0 OID 0)
-- Dependencies: 222
-- Name: SEQUENCE tags_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.tags_primary_key TO cardswap;


--
-- TOC entry 5041 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE tags; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.tags TO cardswap;


--
-- TOC entry 5042 (class 0 OID 0)
-- Dependencies: 233
-- Name: SEQUENCE trades_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.trades_primary_key TO cardswap;


--
-- TOC entry 5043 (class 0 OID 0)
-- Dependencies: 235
-- Name: TABLE trades; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.trades TO cardswap;


--
-- TOC entry 5044 (class 0 OID 0)
-- Dependencies: 219
-- Name: SEQUENCE users_primary_key; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON SEQUENCE public.users_primary_key TO cardswap;


--
-- TOC entry 5045 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE users; Type: ACL; Schema: public; Owner: -
--

GRANT ALL ON TABLE public.users TO cardswap;


--
-- TOC entry 2117 (class 826 OID 16441)
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: public; Owner: -
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON SEQUENCES TO cardswap;


--
-- TOC entry 2118 (class 826 OID 16442)
-- Name: DEFAULT PRIVILEGES FOR FUNCTIONS; Type: DEFAULT ACL; Schema: public; Owner: -
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON FUNCTIONS TO cardswap;


--
-- TOC entry 2116 (class 826 OID 16440)
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: -
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON TABLES TO cardswap;


-- Completed on 2025-02-06 22:46:09

--
-- PostgreSQL database dump complete
--


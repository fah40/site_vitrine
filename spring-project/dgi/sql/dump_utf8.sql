--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


--
-- Name: create_general_info_valeur_for_all_languages(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.create_general_info_valeur_for_all_languages() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    lang_record RECORD;
BEGIN
    -- Boucler sur toutes les langues disponibles dans la table `langue`
    FOR lang_record IN SELECT id FROM langue LOOP
        -- InsÔÇÜrer un enregistrement dans `general_info_valeur` pour chaque langue
        INSERT INTO general_info_valeur (titre, valeur, entete, bouton, id_langue, id_general_info)
        VALUES ('', '', '', '', lang_record.id, NEW.id); -- NEW.id est l'ID du `general_info` insÔÇÜrÔÇÜ
    END LOOP;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.create_general_info_valeur_for_all_languages() OWNER TO postgres;

--
-- Name: insert_attempt_on_user_insert(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.insert_attempt_on_user_insert() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO attempts (date_next_attempt, count_attempt, id_user)
    VALUES (NULL, 0, NEW.id);
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.insert_attempt_on_user_insert() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: actualite; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actualite (
    id bigint NOT NULL,
    titre text NOT NULL,
    description text NOT NULL,
    date_ajout timestamp without time zone NOT NULL,
    id_user uuid NOT NULL
);


ALTER TABLE public.actualite OWNER TO postgres;

--
-- Name: actualite_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.actualite_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.actualite_id_seq OWNER TO postgres;

--
-- Name: actualite_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.actualite_id_seq OWNED BY public.actualite.id;


--
-- Name: attempts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.attempts (
    id bigint NOT NULL,
    date_next_attempt timestamp without time zone,
    count_attempt integer,
    id_user uuid NOT NULL
);


ALTER TABLE public.attempts OWNER TO postgres;

--
-- Name: attempts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.attempts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.attempts_id_seq OWNER TO postgres;

--
-- Name: attempts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.attempts_id_seq OWNED BY public.attempts.id;


--
-- Name: configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.configuration (
    id bigint NOT NULL,
    keys character varying(50),
    valeurs text
);


ALTER TABLE public.configuration OWNER TO postgres;

--
-- Name: configuration_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.configuration_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.configuration_id_seq OWNER TO postgres;

--
-- Name: configuration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.configuration_id_seq OWNED BY public.configuration.id;


--
-- Name: general_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.general_info (
    id bigint NOT NULL,
    cle text NOT NULL,
    icone text,
    lien text,
    id_general_info bigint
);


ALTER TABLE public.general_info OWNER TO postgres;

--
-- Name: general_info_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.general_info_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.general_info_id_seq OWNER TO postgres;

--
-- Name: general_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.general_info_id_seq OWNED BY public.general_info.id;


--
-- Name: general_info_valeur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.general_info_valeur (
    id bigint NOT NULL,
    titre text NOT NULL,
    valeur text NOT NULL,
    entete text,
    bouton text,
    id_langue bigint NOT NULL,
    id_general_info bigint NOT NULL
);


ALTER TABLE public.general_info_valeur OWNER TO postgres;

--
-- Name: general_info_valeur_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.general_info_valeur_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.general_info_valeur_id_seq OWNER TO postgres;

--
-- Name: general_info_valeur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.general_info_valeur_id_seq OWNED BY public.general_info_valeur.id;


--
-- Name: groupe; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.groupe (
    id bigint NOT NULL,
    nom character varying(50) NOT NULL
);


ALTER TABLE public.groupe OWNER TO postgres;

--
-- Name: groupe_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.groupe ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.groupe_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: langue; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.langue (
    id bigint NOT NULL,
    nom character varying(50),
    icon character varying(255)
);


ALTER TABLE public.langue OWNER TO postgres;

--
-- Name: langue_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.langue_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.langue_id_seq OWNER TO postgres;

--
-- Name: langue_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.langue_id_seq OWNED BY public.langue.id;


--
-- Name: piece_jointe; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.piece_jointe (
    id bigint NOT NULL,
    is_image boolean,
    nom_fichier text NOT NULL,
    url_fichier text NOT NULL,
    id_actualite bigint NOT NULL
);


ALTER TABLE public.piece_jointe OWNER TO postgres;

--
-- Name: piece_jointe_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.piece_jointe_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.piece_jointe_id_seq OWNER TO postgres;

--
-- Name: piece_jointe_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.piece_jointe_id_seq OWNED BY public.piece_jointe.id;


--
-- Name: user_; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_ (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(50),
    firstname character varying(50),
    validation_date timestamp without time zone,
    password character varying(255) NOT NULL,
    id_admin boolean DEFAULT false
);


ALTER TABLE public.user_ OWNER TO postgres;

--
-- Name: user_pin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_pin (
    id bigint NOT NULL,
    pin character varying(255) NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    expiration_date timestamp without time zone,
    id_user uuid NOT NULL
);


ALTER TABLE public.user_pin OWNER TO postgres;

--
-- Name: user_pin_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_pin_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_pin_id_seq OWNER TO postgres;

--
-- Name: user_pin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_pin_id_seq OWNED BY public.user_pin.id;


--
-- Name: user_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_token (
    id integer NOT NULL,
    token uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    expiration_date timestamp without time zone,
    id_user uuid NOT NULL
);


ALTER TABLE public.user_token OWNER TO postgres;

--
-- Name: user_token_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_token_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_token_id_seq OWNER TO postgres;

--
-- Name: user_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_token_id_seq OWNED BY public.user_token.id;


--
-- Name: actualite id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actualite ALTER COLUMN id SET DEFAULT nextval('public.actualite_id_seq'::regclass);


--
-- Name: attempts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attempts ALTER COLUMN id SET DEFAULT nextval('public.attempts_id_seq'::regclass);


--
-- Name: configuration id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configuration ALTER COLUMN id SET DEFAULT nextval('public.configuration_id_seq'::regclass);


--
-- Name: general_info id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_info ALTER COLUMN id SET DEFAULT nextval('public.general_info_id_seq'::regclass);


--
-- Name: general_info_valeur id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_info_valeur ALTER COLUMN id SET DEFAULT nextval('public.general_info_valeur_id_seq'::regclass);


--
-- Name: langue id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.langue ALTER COLUMN id SET DEFAULT nextval('public.langue_id_seq'::regclass);


--
-- Name: piece_jointe id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.piece_jointe ALTER COLUMN id SET DEFAULT nextval('public.piece_jointe_id_seq'::regclass);


--
-- Name: user_pin id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_pin ALTER COLUMN id SET DEFAULT nextval('public.user_pin_id_seq'::regclass);


--
-- Name: user_token id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_token ALTER COLUMN id SET DEFAULT nextval('public.user_token_id_seq'::regclass);


--
-- Data for Name: actualite; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actualite (id, titre, description, date_ajout, id_user) FROM stdin;
\.


--
-- Data for Name: attempts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.attempts (id, date_next_attempt, count_attempt, id_user) FROM stdin;
1	\N	0	b49f1f09-6798-421d-9546-f1792d952f39
\.


--
-- Data for Name: configuration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.configuration (id, keys, valeurs) FROM stdin;
1	pin_expiration_minute	10
2	pin_length	6
3	sending_email_pin_content	Nous vous envoyons un pin de connexion.
4	count_attempt	3
5	next_attempt_minute	10
\.


--
-- Data for Name: general_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.general_info (id, cle, icone, lien, id_general_info) FROM stdin;
1	a_propos	\N	\N	\N
7	/historique	\N	\N	\N
13	navigation	\N	\N	\N
14	nav_accueil	\N	#top	13
15	nav_a_propos	\N	#apropos	13
16	nav_documentations	\N	#documentations	13
17	nav_e_service	\N	#safi	13
18	nav_actualites	\N	#actualites	13
19	nav_espace	\N	#torohy	13
20	nav_contact	\N	#contact	13
21	actualites	\N	\N	\N
22	partenaire	\N	\N	\N
26	appeler	\N	#appeler	\N
27	ecrire	\N	#ecrire	\N
28	visiter	\N	#visiter	\N
29	/vision/valeurs et engagements	\N	/admin/explorer?path=/vision/valeurs et engagements	8
30	/vision/projets de modernisation	\N	/admin/explorer?path=/vision/projets de modernisation	8
8	/vision			\N
9	/attributions			\N
34	/legislation/Calendrier Fiscal	\N	/admin/explorer?path=/legislation/Calendrier Fiscal	4
35	/legislation/Conventions et R├®gimes Sp├®ciaux	\N	/admin/explorer?path=/legislation/Conventions et R├®gimes Sp├®ciaux	4
31	/legislation/Recuiel des textes		https://portal.impots.mg/textes/	4
32	/legislation/Rescrit fiscal		https://portal.impots.mg/rescrit/	4
33	/legislation/Brochures et guides		/admin/explorer?path=/legislation/Brochures et guides	4
36	/legislation/Codes et Manuels		/admin/explorer?path=/legislation/Codes et Manuels	4
3	mot_du_dgi			\N
4	/legislation			\N
5	/ressources			\N
40	/ressources/Recrutements	\N	/admin/explorer?path=/ressources/Recrutements	5
41	/ressources/Budget de la DGI	\N	/admin/explorer?path=/ressources/Budget de la DGI	5
6	/analytiques_fiscales			\N
42	/ressources/RCTVA	\N	/admin/explorer?path=/ressources/RCTVA	5
23	bureau			\N
37	/ressources/SIRH		https://sysinfo.mef.gov.mg:9101/ROHI/index.php/accueil/login	5
38	/ressources/G-formation		https://portal.impots.mg/portal/utilisateur	5
24	contribuable			\N
25	recette			\N
39	/ressources/Annuaire		/admin/explorer?path=/ressources/Annuaire	5
10	/e_service			\N
11	/votre_avis			\N
44	/e_service/HetraOnline	\N	https://hetraonline.impots.mg/	10
12	centre_contact			\N
45	/e_service/Valeur administrative des immeubles	\N	https://immo.impots.mg/	10
46	/e_service/Valeur administrative des v├®hicules	\N	https://vehicule.impots.mg/	10
47	/e_service/Attestation de destination	\N	http://www.a2d.impots.mg/	10
48	/e_service/Simulateur de calcul d'imp├┤t	\N	https://www.impots.mg/fr/simulator	10
49	/e_service/DConline	\N	https://entreprises.impots.mg/dconline/	10
50	/e_service/Annexe TVA	\N	https://entreprises.impots.mg/nifonline/annexe_tva.php?bash=02122#accueil_menu_annexe_tva	10
43	/e_service/Ehetra		https://e-hetra.impots.mg/	10
2	dgi			\N
\.


--
-- Data for Name: general_info_valeur; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.general_info_valeur (id, titre, valeur, entete, bouton, id_langue, id_general_info) FROM stdin;
1	Fandraisana	Fandraisana			1	14
2	Momba ny	Momba ny			1	15
3	Tahirin-kevitra	Tahirin-kevitra			1	16
4	E-Service	E-Service			1	17
5	Vaovao	Vaovao			1	18
6	Espace citoyen	Espace citoyen			1	19
7	Ivontoerana fifandraisana	Ivontoerana fifandraisana			1	20
8	Vaovao				1	21
9	Mpiara-miasa				1	22
13	Accueil	Accueil			2	14
14	A propos	A propos			2	15
15	Documentations	Documentations			2	16
16	E-Services	E-Services			2	17
17	ActualitÔÇÜs	ActualitÔÇÜs			2	18
18	Espace citoyen	Espace citoyen			2	19
19	Centre de contact	Centre de contact			2	20
20	actualitÔÇÜs				2	21
21	partenaires				2	22
25	Home	Home			3	14
26	About	About			3	15
27	Documentations	Documentations			3	16
28	E-Services	E-Services			3	17
29	News	News			3	18
30	Citizen Space	Citizen Space			3	19
31	Contact Center	Contact Center			3	20
32	news				3	21
33	partners				3	22
37	Mikasika	Mikasika			1	1
38	A propos	A propos			2	1
39	About	About			3	1
40	Tantara	Tantara ny DGI			1	7
41	Historique	Historique de la DGI			2	7
42	History	History of the DGI			3	7
46	<p>Adidy</p>	<p>Adidy sy andraikitra</p>			1	9
48	<p>Responsibilities</p>	<p>Duties and responsibilities</p>			3	9
59	<p>E-Service</p>	<p>Simplifiez vos obligations fiscales avec SAFI, la plateforme digitale de la Direction G├®n├®rale des Imp├┤ts </p><p>qui vous permet de d├®clarer, payer et suivre vos imp├┤ts en ligne,</p><p> avec un syst├¿me d'alertes personnalis├®es pour </p><p>une gestion optimale de votre compte.</p>	D├®marches fiscales	Se connecter	2	10
61	<p>Legislation</p>	<p>Retrouvez ici l'ensemble des textes et r├®glementations en vigueur r├®gissant la fiscalit├® malgache, r├®guli├¿rement actualis├®s pour vous permettre de mieux comprendre vos droits et obligations fiscales.</p>		show more	3	4
53	<p>Loharano</p>	<p>Acc├®dez ├á toutes les informations sur l'organisation de la Direction G├®n├®rale des Imp├┤ts : son ├®quipe dirigeante, ses responsables et ses structures op├®rationnelles.</p>		hijery	1	5
54	<p>Ressources</p>	<p>Acc├®dez ├á toutes les informations sur l'organisation de la Direction G├®n├®rale des Imp├┤ts : son ├®quipe dirigeante, ses responsables et ses structures op├®rationnelles.</p>		voir plus	2	5
10	<p>Bureaux op├®rationnels</p>	<p><br></p>	153		1	23
22	<p>Bureaux op├®rationnels</p>	<p><br></p>	153		2	23
34	<p>Bureaux op├®rationnels</p>	<p><br></p>	153		3	23
11	<p>contribuables enregistr├®s</p>	<p><br></p>	1585274		1	24
23	<p>contribuables enregistr├®s</p>	<p><br></p>	1585274		2	24
35	<p>contribuables enregistr├®s</p>	<p><br></p>	1585274		3	24
12	<p>recettes collect├®es</p>	<p><br></p>	4754124452922		1	25
24	<p>recettes collect├®es</p>	<p><br></p>	4754124452922		2	25
36	<p>recettes collect├®es</p>	<p><br></p>	4754124452922		3	25
56	<p>Your Opinion</p>	<p>Exprimez votre avis sur nos services et contribuez ├á la lutte contre la fraude fiscale ├á travers notre plateforme s├®curis├®e&nbsp;de&nbsp;signalement.</p>	espace citoyen	Give feedback	3	11
60	<p>L├®gislation</p>	<p>Retrouvez ici l'ensemble des textes et r├®glementations en vigueur r├®gissant la fiscalit├® malgache, r├®guli├¿rement actualis├®s pour vous permettre de mieux comprendre vos droits et obligations fiscales.</p>		voir plus	2	4
49	<p>Votre Avis</p>	<p>Exprimez votre avis sur nos services et contribuez ├á la lutte contre la fraude fiscale ├á travers notre plateforme s├®curis├®e&nbsp;de&nbsp;signalement.</p>	espace citoyen	Donner mon avis	2	11
57	<p>Hevitrao</p>	<p>Exprimez votre avis sur nos services et contribuez ├á la lutte contre la fraude fiscale ├á travers notre plateforme s├®curis├®e&nbsp;de&nbsp;signalement.</p>	espace citoyen	Omeo hevitra	1	11
50	<p>Centre de Contact</p>	<p>Bient├┤t, notre centre d'appel sera disponible pour toute information sur vos d├®marches fiscales. Nos conseillers seront ├á votre ├®coute,&nbsp;24h/24&nbsp;et&nbsp;7j/7.</p>	Nous sommes lÔÇª pour vous	Nous contacter	2	12
51	<p>Contact Center</p>	<p>Bient├┤t, notre centre d'appel sera disponible pour toute information sur vos d├®marches fiscales. Nos conseillers seront ├á votre ├®coute,&nbsp;24h/24&nbsp;et&nbsp;7j/7.</p>	We are here for you	Contact us	3	12
52	<p>Ivontoerana Fifandraisana</p>	<p>Bient├┤t, notre centre d'appel sera disponible pour toute information sur vos d├®marches fiscales. Nos conseillers seront ├á votre ├®coute,&nbsp;24h/24&nbsp;et&nbsp;7j/7.</p>	Miaraka aminay ianao	Mifandraisa aminay	1	12
73	Appeler	SecrÔÇÜtariat DGI : +261 20 85 287 08<br>Cellule Communication DGI : +261 32 12 015 03<br>Cellule eHetra : +261 32 12 015 04<br>Hotline SSIF : +261 32 12 011 74 / +261 34 49 431 52	NOUS CONTACTER		2	26
74	Ecrire	SecrÔÇÜtariat DGI : dgimpots@moov.mg<br>Cellule Communication DGI : communication.dgimpots@gmail.com<br>Hotline SSIF : impot.ssif.hotline@gmail.com			2	27
75	Visiter	Immeuble de l'ÔÇÜconomie et des Finances<br>4┼áme ÔÇÜtage - porte 420<br>Antaninarenina Antananarivo			2	28
76	Call	DGI Secretariat: +261 20 85 287 08<br>DGI Communication Unit: +261 32 12 015 03<br>eHetra Unit: +261 32 12 015 04<br>SSIF Hotline: +261 32 12 011 74 / +261 34 49 431 52	CONTACT US		3	26
77	Write	DGI Secretariat: dgimpots@moov.mg<br>DGI Communication Unit: communication.dgimpots@gmail.com<br>SSIF Hotline: impot.ssif.hotline@gmail.com			3	27
78	Visit	Ministry of Economy and Finance Building<br>4th floor - door 420<br>Antaninarenina Antananarivo			3	28
79	Antsoy	Sekreterian'ny DGI : +261 20 85 287 08<br>Sampan'asa Fampandraharahana DGI : +261 32 12 015 03<br>Sampan'asa eHetra : +261 32 12 015 04<br>Hotline SSIF : +261 32 12 011 74 / +261 34 49 431 52	Hifandray aminay		1	26
80	Hanoratra	Sekreterian'ny DGI : dgimpots@moov.mg<br>Sampan'asa Fampandraharahana DGI : communication.dgimpots@gmail.com<br>Hotline SSIF : impot.ssif.hotline@gmail.com			1	27
81	Hitsidika	Tranon'ny Toekarena sy ny Finansa<br>4┼áme ÔÇÜtage - varavarana 420<br>Antaninarenina Antananarivo			1	28
82					1	29
83					2	29
84					3	29
85					1	30
86					2	30
87					3	30
103	<p><br></p>	<p>Consultez ici l'ensemble des codes fiscaux et manuels de&nbsp;r├®f├®rence.</p>			1	36
47	<p>Attributions</p>	<p>Explorer ici l'organisation </p><p>d├®taill├®e de la DGI </p><p>de Madagascar.</p><p><br></p>			2	9
97					1	34
98					2	34
99					3	34
100					1	35
101					2	35
102					3	35
88	<p><br></p>	<p><br></p>			1	31
89	<p><br></p>	<p><br></p>			2	31
90	<p><br></p>	<p><br></p>			3	31
91	<p><br></p>	<p><br></p>			1	32
92	<p><br></p>	<p><br></p>			2	32
93	<p><br></p>	<p><br></p>			3	32
94	<p><br></p>	<p>Retrouvez tous nos guides et brochures d'information pour vous accompagner dans la compr├®hension et l'accomplissement de vos obligations&nbsp;fiscales.</p>			1	33
95	<p><br></p>	<p>Retrouvez tous nos guides et brochures d'information pour vous accompagner dans la compr├®hension et l'accomplissement de vos obligations&nbsp;fiscales.</p>			2	33
43	<p>Fahitana</p>	<p>T├®l├®chargez ici l'ensemble des documents fondateurs </p><p>qui d├®finissent notre engagement d'excellence </p><p>au service des contribuables et du d├®veloppement&nbsp;├®conomique.</p>			1	8
104	<p><br></p>	<p>Consultez ici l'ensemble des codes fiscaux et manuels de&nbsp;r├®f├®rence.</p>			2	36
105	<p><br></p>	<p>Consultez ici l'ensemble des codes fiscaux et manuels de&nbsp;r├®f├®rence.</p>			3	36
66	<p>E-Service</p>	<p>Simplifiez vos obligations fiscales avec SAFI, la plateforme digitale de la Direction G├®n├®rale des Imp├┤ts </p><p>qui vous permet de d├®clarer, payer et suivre vos imp├┤ts en ligne,</p><p> avec un syst├¿me d'alertes personnalis├®es pour </p><p>une gestion optimale de votre compte.</p>	D├®marches fiscales	Se connecter	1	10
63	<p>Resources</p>	<p>Acc├®dez ├á toutes les informations sur l'organisation de la Direction G├®n├®rale des Imp├┤ts : son ├®quipe dirigeante, ses responsables et ses structures op├®rationnelles.</p>		show more	3	5
64	<p>Famakafakana ara-bola</p>	<p>Consultez dans cette rubrique les documents d'analyse approfondis retra├ºant ses performances en mati├¿re de mobilisation des recettes fiscales, statistiques p├®riodiques des r├®alisations, ├®tudes sectorielles, publications ├®conomiques, rapports d├®taill├®s dÔÇÖactivit├®s, etc.</p>		hijery	1	6
70	<p>Analytiques fiscales</p>	<p>Consultez dans cette rubrique les documents d'analyse approfondis retra├ºant ses performances en mati├¿re de mobilisation des recettes fiscales, statistiques p├®riodiques des r├®alisations, ├®tudes sectorielles, publications ├®conomiques, rapports d├®taill├®s dÔÇÖactivit├®s, etc.</p>		voir plus	2	6
67	<p>Tale jeneralin'ny hetra</p>	<p>Germain</p>	Ny mombamomba		1	2
68	<p>Directeur G├®n├®ral des Imp├┤ts</p>	<p>Germain</p>	├á propos		2	2
69	<p>Director General of Taxes</p>	<p>Germain</p>	about		3	2
62	<p>Lal├ána</p>	<p>Retrouvez ici l'ensemble des textes et r├®glementations en vigueur r├®gissant la fiscalit├® malgache, r├®guli├¿rement actualis├®s pour vous permettre de mieux comprendre vos droits et obligations fiscales.</p>		hijery	1	4
65	<p>E-Service</p>	<p>Simplifiez vos obligations fiscales avec SAFI, la plateforme digitale de la Direction G├®n├®rale des Imp├┤ts </p><p>qui vous permet de d├®clarer, payer et suivre vos imp├┤ts en ligne,</p><p> avec un syst├¿me d'alertes personnalis├®es pour </p><p>une gestion optimale de votre compte.</p>	D├®marches fiscales	Se connecter	3	10
96	<p><br></p>	<p>Retrouvez tous nos guides et brochures d'information pour vous accompagner dans la compr├®hension et l'accomplissement de vos obligations&nbsp;fiscales.</p>			3	33
45	<p>Vision</p>	<p>T├®l├®chargez ici l'ensemble des documents fondateurs </p><p>qui d├®finissent notre engagement d'excellence </p><p>au service des contribuables et du d├®veloppement&nbsp;├®conomique.</p>			3	8
44	<p>Vision</p>	<p>T├®l├®chargez ici l'ensemble des documents fondateurs </p><p>qui d├®finissent notre engagement d'excellence </p><p>au service des contribuables et du d├®veloppement&nbsp;├®conomique.</p>			2	8
55	<p>Tenin'i DGI</p>	<p>Engag├®e dans une transformation digitale majeure ├á travers son nouveau Syst├¿me d'Administration Fiscale Int├®gr├® (SAFI), la DGI vous accueille sur son portail en ligne, incarnant sa vision d'une administration fiscale<strong> innovante, transparente et pilier de l'├®mergence</strong> . D├®couvrez nos services modernes, con├ºus pour garantir votre satisfaction et pr├®server un climat de confiance, conform├®ment ├á lÔÇÖimage dÔÇÖun gouvernement <strong>┬½ manakaiky vahoaka ┬╗</strong>.</p>	DGI		1	3
58	<p>Mot du DGI</p>	<p>Engag├®e dans une transformation digitale majeure ├á travers son nouveau Syst├¿me d'Administration Fiscale Int├®gr├® (SAFI), la DGI vous accueille sur son portail en ligne, incarnant sa vision d'une administration fiscale<strong> innovante, transparente et pilier de l'├®mergence</strong> . D├®couvrez nos services modernes, con├ºus pour garantir votre satisfaction et pr├®server un climat de confiance, conform├®ment ├á lÔÇÖimage dÔÇÖun gouvernement <strong>┬½ manakaiky vahoaka ┬╗</strong>.</p>	DGI		2	3
72	<p>Word of the DGI</p>	<p>Engag├®e dans une transformation digitale majeure ├á travers son nouveau Syst├¿me d'Administration Fiscale Int├®gr├® (SAFI), la DGI vous accueille sur son portail en ligne, incarnant sa vision d'une administration fiscale<strong> innovante, transparente et pilier de l'├®mergence</strong> . D├®couvrez nos services modernes, con├ºus pour garantir votre satisfaction et pr├®server un climat de confiance, conform├®ment ├á lÔÇÖimage dÔÇÖun gouvernement <strong>┬½ manakaiky vahoaka ┬╗</strong>.</p>	DGI		3	3
115					1	40
116					2	40
117					3	40
118					1	41
119					2	41
120					3	41
71	<p>Fiscal analytics</p>	<p>Consultez dans cette rubrique les documents d'analyse approfondis retra├ºant ses performances en mati├¿re de mobilisation des recettes fiscales, statistiques p├®riodiques des r├®alisations, ├®tudes sectorielles, publications ├®conomiques, rapports d├®taill├®s dÔÇÖactivit├®s, etc.</p>		show more	3	6
121					1	42
122					2	42
123					3	42
106	<p><br></p>	<p><br></p>			1	37
107	<p><br></p>	<p><br></p>			2	37
108	<p><br></p>	<p><br></p>			3	37
109	<p><br></p>	<p><br></p>			1	38
110	<p><br></p>	<p><br></p>			2	38
111	<p><br></p>	<p><br></p>			3	38
112	<p><br></p>	<p>Acc├®dez aux coordonn├®es compl├¿tes de nos services centraux et r├®gionaux pour faciliter vos contacts et d├®marches aupr├¿s&nbsp;de&nbsp;la&nbsp;DGI.</p>			1	39
113	<p><br></p>	<p>Acc├®dez aux coordonn├®es compl├¿tes de nos services centraux et r├®gionaux pour faciliter vos contacts et d├®marches aupr├¿s&nbsp;de&nbsp;la&nbsp;DGI.</p>			2	39
114	<p><br></p>	<p>Acc├®dez aux coordonn├®es compl├¿tes de nos services centraux et r├®gionaux pour faciliter vos contacts et d├®marches aupr├¿s&nbsp;de&nbsp;la&nbsp;DGI.</p>			3	39
127					1	44
128					2	44
129					3	44
130					1	45
131					2	45
132					3	45
133					1	46
134					2	46
135					3	46
136					1	47
137					2	47
138					3	47
139					1	48
140					2	48
141					3	48
142					1	49
143					2	49
144					3	49
145					1	50
146					2	50
147					3	50
124	<p><br></p>	<p><br></p>			1	43
125	<p><br></p>	<p><br></p>			2	43
126	<p><br></p>	<p><br></p>			3	43
\.


--
-- Data for Name: groupe; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.groupe (id, nom) FROM stdin;
\.


--
-- Data for Name: langue; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.langue (id, nom, icon) FROM stdin;
1	MLG	\N
2	FRA	\N
3	ENG	\N
\.


--
-- Data for Name: piece_jointe; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.piece_jointe (id, is_image, nom_fichier, url_fichier, id_actualite) FROM stdin;
\.


--
-- Data for Name: user_; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_ (id, email, name, firstname, validation_date, password, id_admin) FROM stdin;
b49f1f09-6798-421d-9546-f1792d952f39	fanasinamanantsoa30@gmail.com		Admin	2025-03-12 00:00:00	$2a$06$pCXEF8jtguYwXZEk3qy8vuWqt8ZgsTIfGp2ZajYvSp2qakZ1Kt9Qq	t
\.


--
-- Data for Name: user_pin; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_pin (id, pin, creation_date, expiration_date, id_user) FROM stdin;
1	922655	2025-03-12 02:52:15.586787	2025-03-12 03:02:15.586787	b49f1f09-6798-421d-9546-f1792d952f39
2	471457	2025-03-12 08:53:21.965224	2025-03-12 09:03:21.965224	b49f1f09-6798-421d-9546-f1792d952f39
3	376341	2025-03-12 09:15:01.27241	2025-03-12 09:25:01.27241	b49f1f09-6798-421d-9546-f1792d952f39
\.


--
-- Data for Name: user_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_token (id, token, creation_date, expiration_date, id_user) FROM stdin;
\.


--
-- Name: actualite_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.actualite_id_seq', 1, false);


--
-- Name: attempts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.attempts_id_seq', 1, true);


--
-- Name: configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.configuration_id_seq', 5, true);


--
-- Name: general_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.general_info_id_seq', 50, true);


--
-- Name: general_info_valeur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.general_info_valeur_id_seq', 147, true);


--
-- Name: groupe_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.groupe_id_seq', 1, false);


--
-- Name: langue_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.langue_id_seq', 1, false);


--
-- Name: piece_jointe_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.piece_jointe_id_seq', 1, false);


--
-- Name: user_pin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_pin_id_seq', 3, true);


--
-- Name: user_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_token_id_seq', 1, false);


--
-- Name: actualite actualite_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actualite
    ADD CONSTRAINT actualite_pkey PRIMARY KEY (id);


--
-- Name: attempts attempts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attempts
    ADD CONSTRAINT attempts_pkey PRIMARY KEY (id);


--
-- Name: configuration configuration_keys_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configuration
    ADD CONSTRAINT configuration_keys_key UNIQUE (keys);


--
-- Name: configuration configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configuration
    ADD CONSTRAINT configuration_pkey PRIMARY KEY (id);


--
-- Name: general_info general_info_cle_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_info
    ADD CONSTRAINT general_info_cle_key UNIQUE (cle);


--
-- Name: general_info general_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_info
    ADD CONSTRAINT general_info_pkey PRIMARY KEY (id);


--
-- Name: general_info_valeur general_info_valeur_id_langue_id_general_info_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_info_valeur
    ADD CONSTRAINT general_info_valeur_id_langue_id_general_info_key UNIQUE (id_langue, id_general_info);


--
-- Name: general_info_valeur general_info_valeur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_info_valeur
    ADD CONSTRAINT general_info_valeur_pkey PRIMARY KEY (id);


--
-- Name: groupe groupe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groupe
    ADD CONSTRAINT groupe_pkey PRIMARY KEY (id);


--
-- Name: langue langue_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.langue
    ADD CONSTRAINT langue_pkey PRIMARY KEY (id);


--
-- Name: piece_jointe piece_jointe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.piece_jointe
    ADD CONSTRAINT piece_jointe_pkey PRIMARY KEY (id);


--
-- Name: configuration uk703e9cfxcq9ia7a3lj3kkjkib; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configuration
    ADD CONSTRAINT uk703e9cfxcq9ia7a3lj3kkjkib UNIQUE (keys);


--
-- Name: user_ user__email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT user__email_key UNIQUE (email);


--
-- Name: user_ user__pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT user__pkey PRIMARY KEY (id);


--
-- Name: user_pin user_pin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_pin
    ADD CONSTRAINT user_pin_pkey PRIMARY KEY (id);


--
-- Name: user_token user_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_token
    ADD CONSTRAINT user_token_pkey PRIMARY KEY (id);


--
-- Name: user_token user_token_token_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_token
    ADD CONSTRAINT user_token_token_key UNIQUE (token);


--
-- Name: general_info trigger_create_general_info_valeur; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trigger_create_general_info_valeur AFTER INSERT ON public.general_info FOR EACH ROW EXECUTE FUNCTION public.create_general_info_valeur_for_all_languages();


--
-- Name: user_ user_insert_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER user_insert_trigger AFTER INSERT ON public.user_ FOR EACH ROW EXECUTE FUNCTION public.insert_attempt_on_user_insert();


--
-- Name: actualite actualite_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actualite
    ADD CONSTRAINT actualite_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.user_(id);


--
-- Name: attempts attempts_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attempts
    ADD CONSTRAINT attempts_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.user_(id);


--
-- Name: general_info general_info_id_general_info_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_info
    ADD CONSTRAINT general_info_id_general_info_fkey FOREIGN KEY (id_general_info) REFERENCES public.general_info(id) ON DELETE CASCADE;


--
-- Name: general_info_valeur general_info_valeur_id_general_info_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_info_valeur
    ADD CONSTRAINT general_info_valeur_id_general_info_fkey FOREIGN KEY (id_general_info) REFERENCES public.general_info(id) ON DELETE CASCADE;


--
-- Name: general_info_valeur general_info_valeur_id_langue_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_info_valeur
    ADD CONSTRAINT general_info_valeur_id_langue_fkey FOREIGN KEY (id_langue) REFERENCES public.langue(id);


--
-- Name: piece_jointe piece_jointe_id_actualite_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.piece_jointe
    ADD CONSTRAINT piece_jointe_id_actualite_fkey FOREIGN KEY (id_actualite) REFERENCES public.actualite(id) ON DELETE CASCADE;


--
-- Name: user_pin user_pin_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_pin
    ADD CONSTRAINT user_pin_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.user_(id);


--
-- Name: user_token user_token_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_token
    ADD CONSTRAINT user_token_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.user_(id);


--
-- PostgreSQL database dump complete
--


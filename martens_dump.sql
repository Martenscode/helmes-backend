--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Debian 16.2-1.pgdg120+2)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: sector; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sector (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    parent_sector_id integer
);


ALTER TABLE public.sector OWNER TO postgres;

--
-- Name: sector_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sector_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sector_id_seq OWNER TO postgres;

--
-- Name: sector_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sector_id_seq OWNED BY public.sector.id;


--
-- Name: user_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_data (
    id integer NOT NULL,
    uuid uuid DEFAULT gen_random_uuid(),
    user_name character varying(255) NOT NULL,
    sector_ids integer[] NOT NULL,
    agree_to_terms boolean NOT NULL
);


ALTER TABLE public.user_data OWNER TO postgres;

--
-- Name: user_data_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_data_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_data_id_seq OWNER TO postgres;

--
-- Name: user_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_data_id_seq OWNED BY public.user_data.id;


--
-- Name: sector id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sector ALTER COLUMN id SET DEFAULT nextval('public.sector_id_seq'::regclass);


--
-- Name: user_data id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data ALTER COLUMN id SET DEFAULT nextval('public.user_data_id_seq'::regclass);


--
-- Data for Name: sector; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sector (id, name, parent_sector_id) FROM stdin;
1	Manufacturing	\N
2	Construction materials	1
3	Electronics and Optics	1
4	Food and Beverage	1
5	Bakery and confectionery products	4
7	Beverages	4
8	Fish & fish products	4
9	Meat & meat products	4
10	Milk & dairy products	4
11	Other	4
12	Sweets & snack food	4
13	Furniture	1
14	Bathroom/sauna	13
15	Bedroom	13
16	Children’s room	13
17	Kitchen	13
18	Living room	13
19	Office	13
20	Other (Furniture)	13
21	Outdoor	13
22	Project furniture	13
23	Machinery	1
24	Machinery components	23
25	Machinery equipment/tools	23
26	Manufacture of machinery	23
27	Maritime	23
28	Aluminium and steel workboats	27
29	Boat/Yacht building	27
30	Ship repair and conversion	27
31	Metal structures	23
32	Other	23
33	Repair and maintenance service	23
34	Metalworking	1
35	Construction of metal structures	34
36	Houses and buildings	34
37	Metal products	34
38	Metal works	34
39	CNC-machining	38
40	Forgings, Fasteners	38
41	Gas, Plasma, Laser cutting	38
42	MIG, TIG, Aluminum welding	38
43	Plastic and Rubber	1
44	Packaging	43
45	Plastic goods	43
46	Plastic processing technology	43
47	Blowing	46
48	Moulding	46
49	Plastics welding and processing	46
50	Plastic profiles	43
51	Printing	1
52	Advertising	51
53	Book/Periodicals printing	51
54	Labelling and packaging printing	51
55	Textile and Clothing	1
56	Clothing	55
57	Textile	55
58	Wood	1
59	Other (Wood)	58
60	Wooden building materials	58
61	Wooden houses	58
62	Other	\N
64	Creative industries	62
65	Energy technology	62
66	Environment	62
67	Service	\N
68	Business services	67
69	Engineering	67
70	Information Technology and Telecommunications	67
71	Data processing, Web portals, E-marketing	70
72	Programming, Consultancy	70
73	Software, Hardware	70
74	Telecommunications	70
75	Tourism	67
76	Translation services	67
77	Transport and Logistics	67
78	Air	77
79	Rail	77
80	Road	77
81	Water	77
\.


--
-- Data for Name: user_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_data (id, uuid, user_name, sector_ids, agree_to_terms) FROM stdin;
\.


--
-- Name: sector_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sector_id_seq', 81, true);


--
-- Name: user_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_data_id_seq', 1, true);


--
-- Name: sector sector_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sector
    ADD CONSTRAINT sector_pkey PRIMARY KEY (id);


--
-- Name: user_data user_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_data_pkey PRIMARY KEY (id);


--
-- Name: sector_id_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX sector_id_index ON public.sector USING btree (id);


--
-- Name: user_data_uuid_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX user_data_uuid_index ON public.user_data USING btree (uuid);


--
-- Name: sector sector_parent_sector_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sector
    ADD CONSTRAINT sector_parent_sector_id_fkey FOREIGN KEY (parent_sector_id) REFERENCES public.sector(id);


--
-- PostgreSQL database dump complete
--


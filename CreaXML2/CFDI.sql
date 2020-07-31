
create table certificados(
	idCertificado int IDENTITY(1,1) PRIMARY KEY,
	nCertificado varchar(30),
	certificado text,
	password varchar(20)
)

--insert into certificados (nCertificado, certificado, password)
--values('00001000000408367933', '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', 'sofom2013');

create table comprobantesNomina(
	idComprobante int IDENTITY(1,1) PRIMARY KEY,
	fecha datetime,
	fechaNomina varchar(7),
	claveContrato varchar(20),
	rfc varchar(13),
	total float,
	--Timbre
	fechaTimbre datetime,
	rfcProv varchar(13),
	uuid varchar(40),
	selloCFD text,
	nCertificado varchar(30),
	selloSAT text,
	idCertificado int,
	FOREIGN KEY (idCertificado) REFERENCES certificados(idCertificado)
)

create table comprobantesEdoCta(
	idComprobante int IDENTITY(1,1) PRIMARY KEY,
	fecha datetime,
	fechaEdoCta varchar(7),
	claveContrato varchar(20),
	total float,
	--Timbre
	fechaTimbre datetime,
	rfcProv varchar(13),
	uuid varchar(40),
	selloCFD text,
	nCertificado varchar(30),
	selloSAT text,
	idCertificado int,
	FOREIGN KEY (idCertificado) REFERENCES certificados(idCertificado)
)
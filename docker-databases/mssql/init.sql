CREATE DATABASE univdb_mssql;
GO

CREATE LOGIN univuser WITH PASSWORD = 'Password123!';
GO

USE univdb_mssql;
GO

CREATE USER univuser FOR LOGIN univuser;
GO

ALTER ROLE db_owner ADD MEMBER univuser;
GO

USE univdb_mssql;
GO

CREATE TABLE Teachers (id INT IDENTITY(1,1) PRIMARY KEY,name NVARCHAR(100) NOT NULL,department NVARCHAR(100));
GO

USE univdb_mssql;
GO

INSERT INTO Teachers (name, department) VALUES ('Prof. Smith', 'Mathematics'),('Prof. Johnson', 'Physics');
GO

USE univdb_mssql;
GO
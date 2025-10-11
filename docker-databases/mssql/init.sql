CREATE DATABASE univdb_mssql;
GO

CREATE LOGIN univuser WITH PASSWORD = 'Password123!';
GO

USE univdb_mssql;
GO

CREATE USER univuser FOR LOGIN univuser;
GO

CREATE SCHEMA univuser AUTHORIZATION univuser;
GO   -- 🔥 acest GO lipsea

ALTER ROLE db_owner ADD MEMBER univuser;
GO

CREATE TABLE Teachers (
                          id INT IDENTITY(1,1) PRIMARY KEY,
                          name NVARCHAR(100) NOT NULL,
                          department NVARCHAR(100)
);
GO

INSERT INTO Teachers (name, department)
VALUES ('Prof. Smith', 'Mathematics'),
       ('Prof. Johnson', 'Physics');
GO

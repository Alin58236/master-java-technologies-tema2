CREATE DATABASE univdb_mssql;
GO
USE univdb_mssql;
GO

CREATE TABLE Teachers (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    department NVARCHAR(100)
);
GO

INSERT INTO Teachers (name, department) VALUES
('Prof. Smith', 'Mathematics'),
('Prof. Johnson', 'Physics');
GO

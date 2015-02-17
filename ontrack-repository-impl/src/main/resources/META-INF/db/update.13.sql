-- #146 Data service

CREATE TABLE ENTITY_DATA (
  ID               INTEGER        NOT NULL AUTO_INCREMENT,
  NAME             VARCHAR(150)   NOT NULL,
  PROJECT          INTEGER        NULL,
  BRANCH           INTEGER        NULL,
  PROMOTION_LEVEL  INTEGER        NULL,
  VALIDATION_STAMP INTEGER        NULL,
  BUILD            INTEGER        NULL,
  PROMOTION_RUN    INTEGER        NULL,
  VALIDATION_RUN   INTEGER        NULL,
  VALUE            VARCHAR(10000) NOT NULL,
  CONSTRAINT ENTITY_DATA_PK PRIMARY KEY (ID),
  CONSTRAINT ENTITY_DATA_FK_PROJECT FOREIGN KEY (PROJECT) REFERENCES PROJECTS (ID)
    ON DELETE CASCADE,
  CONSTRAINT ENTITY_DATA_FK_BRANCH FOREIGN KEY (BRANCH) REFERENCES BRANCHES (ID)
    ON DELETE CASCADE,
  CONSTRAINT ENTITY_DATA_FK_PROMOTION_LEVEL FOREIGN KEY (PROMOTION_LEVEL) REFERENCES PROMOTION_LEVELS (ID)
    ON DELETE CASCADE,
  CONSTRAINT ENTITY_DATA_FK_VALIDATION_STAMP FOREIGN KEY (VALIDATION_STAMP) REFERENCES VALIDATION_STAMPS (ID)
    ON DELETE CASCADE,
  CONSTRAINT ENTITY_DATA_FK_BUILD FOREIGN KEY (BUILD) REFERENCES BUILDS (ID)
    ON DELETE CASCADE,
  CONSTRAINT ENTITY_DATA_FK_PROMOTION_RUN FOREIGN KEY (PROMOTION_RUN) REFERENCES PROMOTION_RUNS (ID)
    ON DELETE CASCADE,
  CONSTRAINT ENTITY_DATA_FK_VALIDATION_RUN FOREIGN KEY (VALIDATION_RUN) REFERENCES VALIDATION_RUNS (ID)
    ON DELETE CASCADE
);

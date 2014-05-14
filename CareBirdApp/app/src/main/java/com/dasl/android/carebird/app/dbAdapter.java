INDX( 	 )m/,           (   �  �       ~ M                   �e     � p     }J    < ���o����o����o��%�o��      �              a p p c o m p a t _ v 7 _ 1 9 _ 1 _ 0 . x m l �e     p Z     }J    < ���o����o����o��%�o��      �              A P P C O M ~ 2 . X M L A P P �k     p ^     }J    < r^�o�}J�o�}J�o����o��      �              c o r e _ 3 _ 0 _ 0 . x m l _ �k     p Z     }J    < r^�o�}J�o�}J�o����o��      �              C O R E _ 3  2 . X M L C O R �k    P � l     }J    < ���o���o�1��o�_�o��      �              s u p p o r t _ v 4 _ 1 9 _ 1 _ 0 . x m l _ _ �k    P p Z     }J    < ���o���o�1��o�_�o��      �              S U P P O R ~ 2 . X M L S U P               _ j b _ o l d _ _ _ o��k    P p Z     }J    < ���o���o�1��o�_�o��      �              S U P P O R ~ 2 . X M L S U P               }J    < ���o���o�1��o�_�o��      �              S U P P O R ~ 2 . X  L S U P               L                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    �k        �    E�go�     ��   �   �       E�go�     �u   m   m      E�go�     ��   y   y      E�go�     �[   w   w      E�gp     ��   y   y      E�gp
     �K   �   �      E�gp     ��   u   u      E�gp     �E   ~   ~        E�gp%                                                                                                                                                                                                                                n TEXT);";
        }

        //creates the pedometer logging table with the below columns. duration i.e. month or week specifies the time duration of the steps_taken value
        private String PedometerLogTable(){
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + PEDOMETER_LOGGER_TABLE +
                    "(" + ID + LOG_TIME + " duration TEXT, steps_taken INTEGER);";
        }

        /*creates the glucose logging table with the below values. "snooze" is determined by a null value in the glucose_value.
        "dismiss" is determined by a null value in the glucose_value and the last entry for the specifies ORIGINAL_ALERT_TIME.
        */
        private String GlucoseLogTable() {
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + GLUCOSE_LOGGER_TABLE +
                    "(" + ID + ORIGINAL_ALERT_TIME + LOG_TIME + " glucose_value REAL);";
        }

        //creates the location log table
        private String LocationLogTable() {
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + LOCATION_LOGGER_TABLE +
                    "(" + ID + ORIGINAL_ALERT_TIME + LOG_TIME + " location TEXT, distance_from_home REAL);";
        }

        //creates the schedule table so alerts are persistent. column names will be the inputs to what ever scheduler we use
        private String ScheduleTable() {
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + SCHEDULE_TABLE +
                    "(" + ID + ORIGINAL_ALERT_TIME + " type TEXT, optional_message TEXT, optional_repeats INTEGER);";
        }

        private String UsersTable() {
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + USERS_TABLE +
                    "(" + ID + LOG_TIME + " type TEXT, user_name TEXT, password TEXT, first_name TEXT, last_name TEXT);";
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //this is version one there is nothing to update

            /*
            Log.w(TAG, "Upgrading database!!!!!");
            //db.execSQL("");
            onCreate(db);
            */
        }

        public void createDataBase() throws IOException {
            boolean dbExist = checkDataBase();
            if (!dbExist) {
                //make sure your database has this table already created in it
                //this does not actually work here
                /*
                 * db.execSQL("CREATE TABLE IF NOT EXISTS \"android_metadata\" (\"locale\" TEXT DEFAULT 'en_US')"
                 * );
                 * db.execSQL("INSERT INTO \"android_metadata\" VALUES ('en_US')"
                 * );
                 */
                this.getReadableDatabase();
                try {
                    copyDataBase();
                } catch (IOException e) {
                    throw new Error("Error copying database");
                }
            }
        }

        public SQLiteDatabase getDatabase() {
            String myPath = DB_PATH + DATABASE_NAME;
            return SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        }

        private boolean checkDataBase() {
            SQLiteDatabase checkDB = null;
            try {
                String myPath = DB_PATH + DATABASE_NAME;
                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READONLY);
            } catch (SQLiteEINDX( 	 �y/,           (     �       �                    �I     h T     oG    ! �ɹo�"O�o�"O�o�"O�o�                       	c o p y r i g h t     �I     h R     oG    ! �ɹo�"O�o�"O�o�"O�o�                       C O P Y R I ~ 1       }J    < h T     oG    ! Q��o�1��o�1��o�1��o�                       	l i b r a r i e s     }J    < h R     oG    ! Q��o�1��o�1��o�1��o�                       L I B R A R ~ 1       /d    + ` N     oG    ! ?	�o '��o�'��o�'��o�                       s c o p e s a }l     p \     oG    ! ���o��m�o����o�B��o� @     �=             w o r k s p a c e . x m l _ _ }l     p Z     oG    ! ���o��m�o����o�B��o� @     �=             W O R K S P ~ 2 . X M L W O R               K S P ~ 2 . X M L W O R               oG    ! ���o��m�o����o�B��o� @     �=             W O R K S P ~ 2 . X M L W O R               L                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
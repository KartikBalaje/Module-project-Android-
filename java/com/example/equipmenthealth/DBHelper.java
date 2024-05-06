package com.example.equipmenthealth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.equipmenthealth.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EquipmentHealth.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_DESIGNATION = "designation";

    private static final String TABLE_COLLEGES = "Colleges";
    private static final String COLUMN_COLLEGE_ID = "college_id";
    private static final String COLUMN_NAME = "name";

    private static final String TABLE_ISSUES = "issues";

    // Column names
    private static final String COLUMN_ISSUE_ID = "issueId";
    private static final String COLUMN_ISSUE_DATE = "issueDate";
    private static final String COLUMN_EQUIPMENT_ID = "equipmentId";
    private static final String COLUMN_ISSUE_LAB_ID = "lab_id";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_ASSIGNED_TO = "assignedTo";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DELIVERY_ON = "deliveryOn";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_DESIGNATION + " TEXT" +
                ")";
        db.execSQL(createUsersTable);
        addUser1(db, "manager", "123", "manager@example.com", "manager");
        addUser1(db, "supervisor", "456", "supervisor@example.com", "supervisor");
        addUser1(db, "technician", "789", "technician@example.com", "technician");


        String createTableQuery = "CREATE TABLE " + TABLE_COLLEGES +
                "(" +
                COLUMN_COLLEGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT" +
                ")";
        db.execSQL(createTableQuery);

        db.execSQL("CREATE TABLE Equipment (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(255),\n" +
                "    purchase_date DATE,\n" +
                "    price DECIMAL(10, 2),\n" +
                "    status VARCHAR(50),\n" +
                "    features TEXT,\n" +
                "    services_undergone TEXT,\n" +
                "    end_of_warranty DATE,\n" +
                "    lab_id INTEGER,\n" +
                "    FOREIGN KEY (lab_id) REFERENCES Lab(lab_id)\n" +
                ");");


        db.execSQL("CREATE TABLE Lab (\n" +
                "    lab_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    lab_name VARCHAR(255),\n" +
                "    college_id INTEGER,\n" +
                "    building_id INTEGER, \n" +
                "    floor_id INTEGER,\n" +
                "    total_rooms INTEGER,\n" +
                "    room_no INTEGER, \n" +
                "    other_details VARCHAR(255),\n" +
                "    FOREIGN KEY (college_id) REFERENCES Colleges(college_id),\n" +
                "    FOREIGN KEY (floor_id) REFERENCES Floor(floor_id),\n" +
                "    FOREIGN KEY (building_id) REFERENCES Building(building_id)\n" +
                ");");

        db.execSQL("CREATE TABLE Floor (\n" +
                "    floor_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    floor_number INTEGER,\n" +
                "    no_of_rooms INTEGER,\n" +
                "    no_of_labs INTEGER,\n" +
                "    college_id INTEGER,\n" +
                "    building_id INTEGER,\n" +
                "    other_details VARCHAR(255),\n" +
                "    FOREIGN KEY (college_id) REFERENCES Colleges(college_id),\n" +
                "    FOREIGN KEY (building_id) REFERENCES Building(building_id)\n" +
                ");");

        db.execSQL("CREATE TABLE Building (\n" +
                "    building_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    no_of_rooms INTEGER,\n" +
                "    total_labs INTEGER,\n" +
                "    college_id INTEGER,\n" +
                "    FOREIGN KEY (college_id) REFERENCES Colleges(college_id)\n" +
                ");");
        db.execSQL("INSERT INTO Colleges (name) VALUES ('Saveetha School of Engineering');");
        db.execSQL("INSERT INTO Colleges (name) VALUES ('SAIL');");
        db.execSQL("INSERT INTO Colleges (name) VALUES ('SCID');");
        db.execSQL("INSERT INTO Colleges (name) VALUES ('Saveetha School of Engineering');");
        db.execSQL("INSERT INTO Colleges (name) VALUES ('AHS');");
        db.execSQL("INSERT INTO Colleges (name) VALUES ('Saveetha Nursing College');");

        String createAssignedCollegesTable = "CREATE TABLE AssignedColleges (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, collegeId INTEGER, FOREIGN KEY (userId) REFERENCES users(id), FOREIGN KEY (collegeId) REFERENCES Colleges(college_id))";
        db.execSQL(createAssignedCollegesTable);

        String createIssuesTableQuery = "CREATE TABLE " + TABLE_ISSUES + " (" +
                COLUMN_ISSUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ISSUE_DATE + " TEXT, " +
                COLUMN_EQUIPMENT_ID + " INTEGER, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_ASSIGNED_TO + " INTEGER REFERENCES users(id), " +
                COLUMN_ISSUE_LAB_ID + " INTEGER REFERENCES Lab(lab_id), " +
                COLUMN_DELIVERY_ON + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT)";

        db.execSQL(createIssuesTableQuery);

        String CREATE_TABLE_RATINGS = "CREATE TABLE Ratings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "rating REAL," +
                "UNIQUE(userId)" +
                ")";
        db.execSQL(CREATE_TABLE_RATINGS);
    }

    private void addUser1(SQLiteDatabase db, String username, String password, String email, String designation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_DESIGNATION, designation);
        db.insert(TABLE_USERS, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Perform upgrade steps for version 2
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_DESIGNATION + " TEXT");
        }
        db.execSQL("DROP TABLE IF EXISTS AssignedColleges");
    }

    public boolean addUser(String username, String password, String email, String designation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_DESIGNATION, designation);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }
    @SuppressLint("Range")
    public User getUserDetails(String username, String password) {
        User user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            String designation = cursor.getString(cursor.getColumnIndex(COLUMN_DESIGNATION));
            user = new User(id, username, password, email, designation);
        }
        cursor.close();
        return user;
    }


    public boolean addBuildingDetails(String name, String buildingId, String noOfRooms, String totalLabs, int collegeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("building_id", buildingId);
        contentValues.put("no_of_rooms", noOfRooms);
        contentValues.put("total_labs", totalLabs);
        contentValues.put("college_id", collegeId); // Change 1 to the actual collegeId

        long result = db.insert("building", null, contentValues);
        return result != -1;
    }

    public List<String> getAllCollegeNames() {
        List<String> collegeNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COLLEGES, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String collegeName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                collegeNames.add(collegeName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return collegeNames;
    }

    @SuppressLint("Range")
    public int getCollegeIdByName(String collegeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_COLLEGE_ID + " FROM " + TABLE_COLLEGES +
                " WHERE " + COLUMN_NAME + " = ?", new String[]{collegeName});
        int collegeId = -1;
        if (cursor.moveToFirst()) {
            collegeId = cursor.getInt(cursor.getColumnIndex(COLUMN_COLLEGE_ID));
        }
        cursor.close();
        return collegeId;
    }

    public boolean addLabDetails(int collegeId, int buildingId, String labName, int floorId, int totalRooms, String otherDetails, int roomNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lab_name", labName);
        contentValues.put("floor_id", floorId);
        contentValues.put("total_rooms", totalRooms);
        contentValues.put("other_details", otherDetails);
        contentValues.put("building_id", buildingId);
        contentValues.put("room_no", roomNo);
        contentValues.put("college_id", collegeId);

        long result = db.insert("Lab", null, contentValues);
        return result != -1;
    }

    public boolean addEquipmentDetails(String name, String purchaseDate, int price, String status, String features, String servicesUndergone, String endOfWarranty, int lab_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("purchase_date", purchaseDate);
        contentValues.put("price", price);
        contentValues.put("status", status);
        contentValues.put("features", features);
        contentValues.put("services_undergone", servicesUndergone);
        contentValues.put("end_of_warranty", endOfWarranty);
        contentValues.put("lab_id", lab_id);

        long result = db.insert("Equipment", null, contentValues);
        return result != -1;
    }

    public boolean addFloorDetails(int floorNumber, int noOfRooms, int noOfLabs, int collegeId, int buildingId, String other_details) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("floor_number", floorNumber);
        contentValues.put("no_of_rooms", noOfRooms);
        contentValues.put("no_of_labs", noOfLabs);
        contentValues.put("college_id", collegeId);
        contentValues.put("building_id", buildingId);
        contentValues.put("other_details", other_details);

        long result = db.insert("Floor", null, contentValues);
        return result != -1;
    }

    public Cursor getLabDetails(int collegeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Lab WHERE college_id = ?", new String[]{String.valueOf(collegeId)});
        return cursor;
    }

    @SuppressLint("Range")
    public List<Lab> getAllLabs() {
        List<Lab> labsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query to select all labs
            String selectQuery = "SELECT * FROM Lab";

            cursor = db.rawQuery(selectQuery, null);

            // Loop through all rows and add them to the list
            if (cursor.moveToFirst()) {
                do {
                    Lab lab = new Lab();
                    lab.setLabId(cursor.getInt(cursor.getColumnIndex("lab_id")));
                    lab.setLabName(cursor.getString(cursor.getColumnIndex("lab_name")));

                    labsList.add(lab);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while fetching all labs: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return labsList;
    }


    @SuppressLint("Range")
    public List<Floor> getFloorsByBuildingId(int buildingId) {
        List<Floor> floorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Floor WHERE building_id = ?", new String[]{String.valueOf(buildingId)});
        if (cursor.moveToFirst()) {
            do {
                Floor floor = new Floor();
                floor.setFloorId(cursor.getInt(cursor.getColumnIndex("floor_id")));
                floor.setFloorNumber(cursor.getInt(cursor.getColumnIndex("floor_number")));
                floorList.add(floor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return floorList;
    }

    @SuppressLint("Range")
    public List<Building> getBuildingsByCollegeId(int collegeId) {
        List<Building> buildingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Building WHERE college_id = ?", new String[]{String.valueOf(collegeId)});
        if (cursor.moveToFirst()) {
            do {
                Building building = new Building();
                building.setBuildingId(cursor.getInt(cursor.getColumnIndex("building_id")));
                building.setName(cursor.getString(cursor.getColumnIndex("name")));
                buildingList.add(building);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return buildingList;
    }

    @SuppressLint("Range")
    public List<College> getAllColleges() {
        List<College> collegeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Colleges", null);
        if (cursor.moveToFirst()) {
            do {
                College college = new College();
                college.setCollegeId(cursor.getInt(cursor.getColumnIndex("college_id")));
                college.setName(cursor.getString(cursor.getColumnIndex("name")));
                collegeList.add(college);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return collegeList;
    }


    public List<User> getUsersByDesignation(String designation) {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "id",
                "username",
                "designation",
                "password",
                "email"
        };

        String selection = "designation = ?";
        String[] selectionArgs = { designation };

        Cursor cursor = db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String userDesignation = cursor.getString(cursor.getColumnIndexOrThrow("designation"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

            User user = new User(id, name, password, email, userDesignation);
            userList.add(user);
        }

        cursor.close();
        return userList;
    }


    public boolean addAssignedCollege(int userId, int collegeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("collegeId", collegeId);

        long result = db.insert("AssignedColleges", null, values);
        return result != -1;
    }

    @SuppressLint("Range")
    public int getAssignedCollegeId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int collegeId = -1;

        String[] columns = {"collegeId"};
        String selection = "userId = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query("AssignedColleges", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            collegeId = cursor.getInt(cursor.getColumnIndex("collegeId"));
        }

        cursor.close();
        return collegeId;
    }

    public College getCollegeById(int collegeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        College college = null;

        String[] columns = {"name"};
        String selection = "college_id = ?";
        String[] selectionArgs = {String.valueOf(collegeId)};
        Cursor cursor = db.query("Colleges", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            college = new College(collegeId, name);
        }

        cursor.close();
        return college;
    }

    @SuppressLint("Range")
    public List<Lab> getLabsListById(int college_id) {
        List<Lab> labList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Lab WHERE college_id=?", new String[]{String.valueOf(college_id)});
        if (cursor.moveToFirst()) {
            do {
                Lab lab = new Lab();
                lab.setLabId(cursor.getInt(cursor.getColumnIndex("lab_id")));
                lab.setLabName(cursor.getString(cursor.getColumnIndex("lab_name")));
                lab.setCollege_id(cursor.getInt(cursor.getColumnIndex("college_id")));
                lab.setFloorId(cursor.getInt(cursor.getColumnIndex("floor_id")));
                lab.setTotalRooms(cursor.getInt(cursor.getColumnIndex("total_rooms")));
                lab.setNumberOfLabs(cursor.getInt(cursor.getColumnIndex("room_no")));
                lab.setOtherDetails(cursor.getString(cursor.getColumnIndex("other_details")));
                labList.add(lab);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labList;
    }

    @SuppressLint("Range")
    public List<Equipment> getEquipmentsByLabId(int labId) {
        List<Equipment> equipmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Equipment WHERE lab_id=?", new String[]{String.valueOf(labId)});
        if (cursor.moveToFirst()) {
            do {
                Equipment equipment = new Equipment();
                equipment.setId(cursor.getInt(cursor.getColumnIndex("id")));
                equipment.setName(cursor.getString(cursor.getColumnIndex("name")));
                equipment.setPurchaseDate(cursor.getString(cursor.getColumnIndex("purchase_date")));
                equipment.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                equipment.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                equipment.setFeatures(cursor.getString(cursor.getColumnIndex("features")));
                equipment.setServicesUndergone(cursor.getString(cursor.getColumnIndex("services_undergone")));
                equipment.setEndOfWarranty(cursor.getString(cursor.getColumnIndex("end_of_warranty")));
                equipment.setLabId(cursor.getInt(cursor.getColumnIndex("lab_id")));
                equipmentList.add(equipment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return equipmentList;
    }

    public boolean insertIssue(String issueDate, int labId, int equipmentId, String status, int assignedTo, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ISSUE_DATE, issueDate);
        contentValues.put(COLUMN_EQUIPMENT_ID, equipmentId);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_ASSIGNED_TO, assignedTo);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_ISSUE_LAB_ID, labId);

        long result = db.insert(TABLE_ISSUES, null, contentValues);
        return result != -1;
    }
    @SuppressLint("Range")
    public List<Issue> getAllIssues() {
        List<Issue> issues = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ISSUES, null);

        if (cursor.moveToFirst()) {
            do {
                Issue issue = new Issue();
                issue.setIssueId(cursor.getInt(cursor.getColumnIndex("issueId")));
                issue.setIssueDate(cursor.getString(cursor.getColumnIndex("issueDate")));
                issue.setEquipmentId(cursor.getInt(cursor.getColumnIndex("equipmentId")));
                issue.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                issue.setAssignedTo(cursor.getInt(cursor.getColumnIndex("assignedTo")));
                issue.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                issue.setLabId(cursor.getInt(cursor.getColumnIndexOrThrow("lab_id")));
                issues.add(issue);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return issues;
    }
    @SuppressLint("Range")
    public Equipment getEquipmentById(int equipmentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Equipment WHERE id = ?", new String[]{String.valueOf(equipmentId)});
        Equipment equipment = null;
        if (cursor.moveToFirst()) {
            equipment = new Equipment();
            equipment.setId(cursor.getInt(cursor.getColumnIndex("id")));
            equipment.setName(cursor.getString(cursor.getColumnIndex("name")));
            equipment.setPurchaseDate(cursor.getString(cursor.getColumnIndex("purchase_date")));
            equipment.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            equipment.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            equipment.setFeatures(cursor.getString(cursor.getColumnIndex("features")));
            equipment.setServicesUndergone(cursor.getString(cursor.getColumnIndex("services_undergone")));
            equipment.setEndOfWarranty(cursor.getString(cursor.getColumnIndex("end_of_warranty")));
            equipment.setLabId(cursor.getInt(cursor.getColumnIndex("lab_id")));
        }
        cursor.close();
        db.close();
        return equipment;
    }

    @SuppressLint("Range")
    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE id = ?", new String[]{String.valueOf(userId)});
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            // Add other user attributes here
        }
        cursor.close();
        db.close();
        return user;
    }
    @SuppressLint("Range")
    public Lab getLabById(int labId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Lab WHERE lab_id = ?", new String[]{String.valueOf(labId)});
        Lab lab = null;
        if (cursor.moveToFirst()) {
            lab = new Lab();
            lab.setLabId(cursor.getInt(cursor.getColumnIndex("lab_id")));
            lab.setLabName(cursor.getString(cursor.getColumnIndex("lab_name")));
            lab.setFloorId(cursor.getInt(cursor.getColumnIndex("floor_id")));
            lab.setTotalRooms(cursor.getInt(cursor.getColumnIndex("total_rooms")));
            lab.setNumberOfLabs(cursor.getInt(cursor.getColumnIndex("room_no")));
            lab.setOtherDetails(cursor.getString(cursor.getColumnIndex("other_details")));
        }
        cursor.close();
        db.close();
        return lab;
    }

    public void updateIssueAssignedTo(int issueId, int assignedToId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("assignedTo", assignedToId);
        String selection = "issueId = ?";
        String[] selectionArgs = { String.valueOf(issueId) };
        db.update("issues", values, selection, selectionArgs);
        db.close();
    }

    @SuppressLint("Range")
    public String getCollegeNameByLabId(int labId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String collegeName = "";

        String query = "SELECT name FROM Colleges WHERE college_id = (SELECT college_id FROM Lab WHERE lab_id = "+labId+")";


        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            collegeName = cursor.getString(cursor.getColumnIndex("name"));
            cursor.close();
        }

        return collegeName;
    }

    public void updateIssueStatus(int issueId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);

        db.update(TABLE_ISSUES, values, COLUMN_ISSUE_ID + " = ?", new String[] { String.valueOf(issueId) });
        db.close();
    }

    public void insertOrUpdateRating(int userId, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("rating", rating);

        // Check if userId already exists
        Cursor cursor = db.rawQuery("SELECT rating FROM Ratings WHERE userId = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            // User exists, update the rating
            @SuppressLint("Range") float currentRating = cursor.getFloat(cursor.getColumnIndex("rating"));
            float newRating = (currentRating + rating) / 2; // Calculate average
            values.put("rating", newRating);
            db.update("Ratings", values, "userId = ?", new String[]{String.valueOf(userId)});
        } else {
            // User doesn't exist, insert new row
            db.insert("Ratings", null, values);
        }

        cursor.close();
        db.close();
    }

    public List<String> getUsernamesFromAssignedTo() {
        List<String> usernames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT userId FROM AssignedColleges", null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("userId"));
            // Assuming you have a method to get username by userId
            String username = getUsernameByUserId(userId);
            usernames.add(username);
        }
        cursor.close();
        db.close();
        return usernames;
    }

    @SuppressLint("Range")
    public String getUsernameByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query("Users", columns, selection, selectionArgs, null, null, null);
        String username = null;
        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex("username"));
            cursor.close();
        }
        db.close();
        return username;
    }


    @SuppressLint("Range")
    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1; // Default value if user not found

        String query = "SELECT id FROM Users WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"));
        }

        cursor.close();
        db.close();
        return userId;
    }


    public List<Integer> getPendingEquipmentIds() {
        List<Integer> equipmentIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { "equipmentId" };
        String selection = "status = ?";
        String[] selectionArgs = { "Pending" };
        Cursor cursor = db.query("issues", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int equipmentId = cursor.getInt(cursor.getColumnIndex("equipmentId"));
                equipmentIds.add(equipmentId);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return equipmentIds;
    }

    public void updateIssueStatusByEquipmentId(int equipmentId, String status, String deliveryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", status);
        if (deliveryDate != null) {
            values.put("deliveryOn", deliveryDate);
        }

        String whereClause = "equipmentId = ?";
        String[] whereArgs = { String.valueOf(equipmentId) };

        db.update("issues", values, whereClause, whereArgs);
        db.close();
    }

}

package licence.projet.oblika.model;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class DataBaseHandler {

    private static String pseudo = "Guest";
    private static FirebaseUser user;
    private static FirebaseAuth mAuth;
    private static Context context;
    private static DatabaseReference mDatabase;
    private static User userObj;

    public static void init(Context context) {
        DataBaseHandler.mAuth = FirebaseAuth.getInstance();
        DataBaseHandler.context = context;
        DataBaseHandler.mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
        update();
    }

    public static String getPseudo() {
        return pseudo;
    }

    public static void setPseudo(String pseudo) {

        DataBaseHandler.pseudo = pseudo;
    }


    private boolean isInfoValid(String mail, String password) {
        return false;
    }


    public static void deleteUser() {
        if (user != null) {
            mDatabase.child("users").child(user.getUid()).removeValue();
            user.delete();
            mAuth.signOut();
        }
        DataBaseHandler.setPseudo("Guest");
    }

    public static void login(FirebaseUser user) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user1 = dataSnapshot.getValue(User.class);
                userObj = user1;
                if (user1 != null) pseudo = user1.getPseudo();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    public static void register(FirebaseUser user, String pseudo) {
        update();
        if (DataBaseHandler.user == null) return;
        addPseudoToUser(user, pseudo);
        addNewUser(user);
    }

    private static void addNewUser(FirebaseUser user) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        User user1 = new User(pseudo, user.getEmail());
        mDatabase.child("users").child(user.getUid()).setValue(user1);
    }

    public static void addPseudoToUser(FirebaseUser user, String pseudo) {
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(pseudo).build();
    }

    public static void update() {
        user = mAuth.getCurrentUser();
        if (user != null) pseudo = user.getDisplayName();
        login(user);
    }
}

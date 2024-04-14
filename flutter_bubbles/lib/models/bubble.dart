import 'package:cloud_firestore/cloud_firestore.dart';

class Bubble {
  static const String fieldFirestoreUserID = 'firestoreUserId';
  String firestoreId = ""; // document id inside fire store
  String firestoreUserId = ""; // user id for user specific data inside fire store
  String name = ""; // Name of bubble is required
  Bubble();

  // reads person from FireStore db data object
  Bubble.fromFireStore(QueryDocumentSnapshot<Object?> data) {
    firestoreId = data.id;
    firestoreUserId = data.get(fieldFirestoreUserID);
    name = data.get('name');
  }

  Map<String, dynamic> toFireStore() {
    return {
      'name': name,
      fieldFirestoreUserID : firestoreUserId
    };
  }

  // stores firestore user at model (at insert in to fireStore)
  void setFireStoreUser(String userid) {
    firestoreUserId = userid;
  }
}

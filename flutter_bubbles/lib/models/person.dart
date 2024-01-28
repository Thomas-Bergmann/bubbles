import 'package:cloud_firestore/cloud_firestore.dart';

class Person {
  static const String fieldFirestoreUserID = 'firestoreUserId';
  String firestoreId = ""; // id inside fire store
  String firestoreUserId = ""; // user id for user specific data inside fire store
  String name = ""; // Name is required
  DateTime? birthday; // birthday
  DateTime? nextBirthday;
  Person();

  // reads person from FireStore db data object
  Person.fromFireStore(QueryDocumentSnapshot<Object?> data) {
    firestoreId = data.id;
    firestoreUserId = data.get(fieldFirestoreUserID);
    name = data.get('name');
    birthday = data.get('birthday')?.toDate();
  }

  Map<String, dynamic> toFireStore() {
    return {
      'name': name,
      'birthday': birthday == null ? null : Timestamp.fromDate(birthday!),
      fieldFirestoreUserID : firestoreUserId
    };
  }

  // stores firestore user at model (at insert in to fireStore)
  void setFireStoreUser(String userid) {
    firestoreUserId = userid;
  }
}

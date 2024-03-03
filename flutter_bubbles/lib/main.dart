import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bubbles/database/remote/firestore_user.dart';
import 'package:flutter_bubbles/models/auth_model.dart';
import 'package:flutter_bubbles/models/person_model.dart';
import 'package:provider/provider.dart';

import 'package:flutter_bubbles/models/preference_model.dart';
import 'package:flutter_bubbles/pages/home_page.dart';
import 'package:firebase_core/firebase_core.dart';
import 'firebase_options.dart';

// ...

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  await FirebaseAuth.instance.setPersistence(Persistence.LOCAL);
  runApp(const MainApp());
}

class MainApp extends StatefulWidget {
  const MainApp({super.key});
  @override
  State<StatefulWidget> createState() => MainAppState();
}

class MainAppState extends State<MainApp> {
  bool isInitialized = false;

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
        // https://www.youtube.com/watch?v=T9SG5nkWA7E min 1
        providers: [
          ChangeNotifierProvider(create: (context) => PreferenceModel()),
          ChangeNotifierProvider(create: (context) => PersonModel()),
          ChangeNotifierProvider(create: (context) => AuthModel()),
        ],
        builder: (context, child) => _consumer(context));
  }

  Consumer<PreferenceModel> _consumer(BuildContext context) {
    initializeAfterLogin(context);
    return Consumer<PreferenceModel>(
        builder: (context, preferences, child) => MaterialApp(
              theme: preferences.bubbleTheme.light(),
              darkTheme: preferences.bubbleTheme.dark(),
              themeMode: preferences.themeMode,
              // debugShowCheckedModeBanner: false,
              home: const HomePage(),
            ));
  }

  void initializeAfterLogin(BuildContext context) {
    AuthModel authModel = Provider.of<AuthModel>(context);
    if (authModel.isLoggedIn()) {
      String userID = authModel.getUserID() ?? '';
      print('user is logged-in $userID');
      Provider.of<PersonModel>(context).setUser(FireStoreUser(userid: userID));
    }
  }
}

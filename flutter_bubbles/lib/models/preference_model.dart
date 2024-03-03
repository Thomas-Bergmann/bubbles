import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/themes.dart';

class PreferenceModel extends ChangeNotifier {
  ThemeMode _themeMode = ThemeMode.system;
  ThemeMode get themeMode => _themeMode;

  String _locale = 'de_DE';
  String get locale => _locale;

  BubbleTheme get bubbleTheme => BubbleTheme();

  void setThemeMode(ThemeMode themeMode) {
    _themeMode = themeMode;
    notifyListeners();
  }

  void setLocale(String locale) {
    _locale = locale;
    notifyListeners();
  }
}

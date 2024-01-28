import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class BubbleTheme {
  // no usage yet
  Widget appBarLeftIcon = SvgPicture.asset(
    'assets/icons/arrow-left.svg',
    height: 50,
    width: 50,
  );
  final ThemeData _light = _getLightTheme();
  final ThemeData _dark = _getDarkTheme();

  light() {
    return _light;
  }

  dark() {
    return _dark;
  }
}

// https://www.youtube.com/watch?v=ZUi3hppgG2s color scheme
ThemeData _getLightTheme() {
  return ThemeData(
    brightness: Brightness.light,
    useMaterial3: true,
    colorSchemeSeed: Colors.blue,
    appBarTheme: const AppBarTheme(
      backgroundColor: Colors.blue,
      foregroundColor: Colors.white,
    ),
    textTheme: const TextTheme(bodyLarge: TextStyle(), bodySmall: TextStyle()),
  );
}

ThemeData _getDarkTheme() {
  return ThemeData(
    brightness: Brightness.dark,
    useMaterial3: true,
    colorSchemeSeed: Colors.green,
  );
}

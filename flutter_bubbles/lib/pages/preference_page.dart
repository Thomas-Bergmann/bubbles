import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/preference_model.dart';
import 'package:flutter_bubbles/widgets/app_bar.dart';
import 'package:provider/provider.dart';

class PreferencesPage extends StatelessWidget {
  const PreferencesPage({super.key});

  @override
  build(BuildContext context) {
    return Consumer<PreferenceModel>(
        builder: (context, model, child) => getScaffold(context, model));
  }

  Scaffold getScaffold(BuildContext context, PreferenceModel model) {
    return Scaffold(
      appBar: createSimpleAppBar(context, 'Preferences'),
      body: getBody(model),
    );
  }

  Column getBody(PreferenceModel model) {
    List<Widget> rows = List.empty(growable: true);
    rows.add(getThemeRow(model));
    return Column(children: rows);
  }

  Row getThemeRow(PreferenceModel model) {
    bool isDarkMode = ThemeMode.dark == model.themeMode;
    return Row(
      children: [
        const Text("DarkMode:"),
        Switch(
          value: isDarkMode,
          onChanged: (isOn) {
            isOn
                ? model.setThemeMode(ThemeMode.dark)
                : model.setThemeMode(ThemeMode.light);
          },
        )
      ],
    );
  }
}

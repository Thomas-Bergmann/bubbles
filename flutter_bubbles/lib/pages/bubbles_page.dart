import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/preference_model.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

import 'package:flutter_bubbles/models/bubble.dart';
import 'package:flutter_bubbles/models/bubble_dao.dart';
import 'package:flutter_bubbles/pages/bubble_detail_page.dart';
import 'package:flutter_bubbles/widgets/app_bar.dart';
import 'package:flutter_bubbles/widgets/bubble_tile.dart';

class BubblesPage extends StatelessWidget {
  const BubblesPage({super.key});

  @override
  build(BuildContext context) {
    return Consumer2<BubbleDao, PreferenceModel>(
        builder: (context, bubbleDao, preferenceMode, child) => Scaffold(
              appBar: createAppBarWithAdd(
                  context,
                  'Bubbles (${bubbleDao.getAll().length})',
                  () => _navigateToAddBubble(context)),
              body: _getBubblesBody(context, bubbleDao, preferenceMode),
            ));
  }

  // https://www.youtube.com/watch?v=RPvhoghXn54 minute 10
  Widget _getBubblesBody(BuildContext context, BubbleDao bubbleDao,
      PreferenceModel preferenceModel) {
    final bubbles = bubbleDao.getAll();
    print('BubblesPage: bubbles ${bubbles.length} @ ${bubbleDao.uuid}');
    return Column(children: [
      Expanded(
        child: ListView.builder(
            itemCount: bubbles.length,
            itemBuilder: (context, index) => BubbleTile(
                dateFormat: DateFormat.yMMMd(preferenceModel.locale),
                onTap: () => _navigateToBubble(context, bubbles[index]),
                bubble: bubbles[index])),
      ),
    ]);
  }

  void _navigateToBubble(BuildContext context, Bubble bubble) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) =>
                BubbleDetailPage(bubble: bubble, isNew: false)));
  }

  void _navigateToAddBubble(BuildContext context) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) =>
                BubbleDetailPage(bubble: Bubble(), isNew: true)));
  }
}

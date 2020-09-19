import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '干货集中营',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: '干货集中营'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List _list = [];
  int _page = 1;
  bool hasMore = true;
  ScrollController _scrollController = new ScrollController();

  @override
  void initState() {
    super.initState();
    this._getListData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body:this._list.length>0?ListView.builder(
          itemCount: this._list.length,
          itemBuilder: _itemBuild)
          :Center(
        child: Text('加载中...'),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  void _getListData() async{
    if (this.hasMore) {
      var dioUrl =
          'https://gank.io/api/v2/data/category/Girl/type/Girl/page/${this._page}/count/10';
      Response response = await Dio().get(dioUrl);
      var arr = json.decode(response.toString())['data'];
      print(arr);
      setState(() {
        if (this._page == 1) {
          this._list = arr;
        } else {
          this._list.addAll(arr);
        }
        this._page++;
      });
      //判断是否为最后一页
      if (arr.length < 10) {
        setState(() {
          this.hasMore = false;
        });
      }
    }
  }

  Widget _itemBuild(BuildContext context, int index) {
    return Container(
      child: Image.network(this._list[index]['url']),
    );
  }
}

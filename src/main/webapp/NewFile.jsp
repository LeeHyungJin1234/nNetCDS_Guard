                     <html>
<head>
  <script src="https://cdn.anychart.com/releases/v8/js/anychart-base.min.js"></script>
  <script src="https://cdn.anychart.com/releases/v8/js/anychart-graph.min.js"></script>
  <script src="https://cdn.anychart.com/releases/v8/js/anychart-data-adapter.min.js"></script>
  <script src="https://cdn.anychart.com/releases/v8/js/anychart-ui.min.js"></script>
  <script src="https://cdn.anychart.com/releases/v8/js/anychart-exports.min.js"></script>
  <script src="https://cdn.anychart.com/releases/v8/themes/dark_blue.min.js"></script>
  <link href="https://cdn.anychart.com/releases/v8/css/anychart-ui.min.css" type="text/css" rel="stylesheet">
  <link href="https://cdn.anychart.com/releases/v8/fonts/css/anychart-font.min.css" type="text/css" rel="stylesheet">
  <style type="text/css">

    html,
    body,
    #container {
      width: 100%;
      height: 100%;
      margin: 0;
      padding: 0;
    }

    .anychart-tooltip {
      width: 300px;
      background: white;
      color: black;
      box-shadow: 5px 5px 18px #333;
    }

    .anychart-tooltip h5 {
      text-align: center;
      margin: 5px 0;
    }

    .anychart-tooltip img {
      display: block;
      margin: 0 auto;
      max-height: 250px;
    }
  
</style>
</head>
<body>
  
  <div id="container"></div>
  

  <script>

    anychart.onDocumentReady(function () {
	// set chart theme
anychart.theme('darkBlue');
      anychart.data.loadJsonFile(
        // The data used in this sample can be obtained from the CDN
        'js/data.json',
        function (data) {
          // create graph chart
          var chart = anychart.graph(data);

          // set chart layout type
          chart.layout().type('fixed');

          // disable chart tooltips
          chart.tooltip(false);

          // enable chart nodes labels
          chart.nodes().labels().enabled(true);

          // set node labels text formatter
          chart
            .nodes()
            .labels()
            .format(function () {
              var text = this.getData('group');
              if (text !== 'break') {
                return this.getData('group');
              }
              return '';
            });

          // set line breaks group nodes settings
          var breakGroup = chart.group('break');
          breakGroup.height(0);
          breakGroup.stroke('none');

          // set pc group nodes settings
          var pcGroup = chart.group('pc');
          pcGroup.fill({
            src:
              'https://cdn.anychart.com/samples-data/graph/network-graph/pc.svg',
            mode: 'fit'
          });
          pcGroup.shape('rectangle');
          pcGroup.stroke('none');
          pcGroup.width(45);
          pcGroup.height(45);

          // set pc group nodes settings
          var routerGroup = chart.group('router');
          routerGroup.fill({
            src:
              'https://cdn.anychart.com/samples-data/graph/network-graph/router.svg',
            mode: 'fit'
          });
          routerGroup.height(70);
          routerGroup.shape('square');
          routerGroup.stroke('none');

          // set internet group nodes settings
          var internetGroup = chart.group('internet');
          internetGroup.height(60);
          internetGroup.shape('square');
          internetGroup.stroke('none');
          internetGroup.fill({
            src:
              'https://cdn.anychart.com/samples-data/graph/network-graph/isp.svg',
            mode: 'fit'
          });

          // set cloud group nodes settings
          var cloudGroud = chart.group('cloud');
          cloudGroud.height(60);
          cloudGroud.shape('square');
          cloudGroud.stroke('none');
          cloudGroud.fill({
            src:
              'https://cdn.anychart.com/samples-data/graph/network-graph/cloud-server.png',
            mode: 'fit'
          });

          // set switch group nodes settings
          var switchGroup = chart.group('switch');
          switchGroup.shape('rectangle');
          switchGroup.width(120);
          switchGroup.height(40);
          switchGroup.stroke('none');
          switchGroup.fill({
            src:
              'https://cdn.anychart.com/samples-data/graph/network-graph/switch.svg',
            mode: 'fit'
          });

          // set server group nodes settings
          var serverGroup = chart.group('server');
          serverGroup.shape('square');
          serverGroup.height(70);
          serverGroup.stroke('none');
          serverGroup.fill({
            src:
              'https://cdn.anychart.com/samples-data/graph/network-graph/server.svg',
            mode: 'fit'
          });

          // set printer group nodes settings
          var printerGroup = chart.group('printer');
          printerGroup.shape('square');
          printerGroup.height(50);
          printerGroup.stroke('none');
          printerGroup.fill({
            src:
              'https://cdn.anychart.com/samples-data/graph/network-graph/printer.svg',
            mode: 'fit'
          });

          // set mobile group nodes settings
          var mobileGroup = chart.group('mobile');
          mobileGroup.shape('square');
          mobileGroup.height(60);
          mobileGroup.stroke('none');
          mobileGroup.fill({
            src:
              'https://cdn.anychart.com/samples-data/graph/network-graph/mobile.svg',
            mode: 'fit'
          });

          // enable edge arrows and set arrow size
          var arrows = chart.edges().arrows();
          arrows.enabled(true);
          arrows.size(8);

          // set container id for the chart
          chart.container('container');
          // initiate chart drawing
          chart.draw();
        }
      );
    });
  
</script>
</body>
</html>
                
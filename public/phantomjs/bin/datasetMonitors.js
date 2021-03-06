$(function () {$('#container').highcharts({
    chart : {
      plotBackgroundColor : null,
      plotBorderWidth : null,
      plotShadow : false,
      type : 'column',
      zoomType : 'xy'
    },
    title : {
      text : ''
    },
    tooltip : {
      formatter : '$TOOLTIPS-FUNC'
    },
    legend : {
      layout : 'horizontal',
      align : 'right',
      verticalAlign : 'bottom',
      y : 10,
      floating : false,
      borderWidth : 0,
      itemDistance : 5,
      backgroundColor : '#FFFFFF',
      margin : 5,
      shadow : false
    },
    plotOptions : {
      column : {
        lineWidth : 1,
        dataLabels : {
          enabled : true,
          color : '#fff'
        },
        stacking : 'normal'
      }
    },
    credits : {
      enabled : false
    },
    xAxis : {
      min : 0,
      title : {
        text : 'Person',
        y : '2',
        margin : 0
      },
      labels : {
        
      },
      categories : ['Alexander Voyevodin','Alexey Popov','Anastasia Demchenko','Andrey Kucherov','Anton Kovalev','Denis Demianko','Dmitry Tatievskyi','Eugeniy Malysh','James Simpson','James Simpson [Administrator]','Roman Radzhab','Viral Panchal','Vitaliy Grenkow','Vitaliy Kvas','Yuriy Kondrat']
    },
    yAxis : {
      title : {
        text : 'Number of Issues'
      },
      gridLineDashStyle : 'Dash'
    },
    series : [
      {
        name : 'Open',
        data : [4,4,3,2,3,6,2,0,12,7,0,14,4,1,1],
        color : '#0E63B2'
      },
      {
        name : 'InProgress',
        data : [2,2,2,1,1,4,0,2,0,1,2,1,2,1,0],
        color : '#00C000'
      },
      {
        name : 'ResolvedInPeriod',
        data : [2,1,0,0,2,3,0,0,0,0,2,0,0,2,0],
        color : '#7F7F7F'
      },
      {
        name : 'ClosedInPeriod',
        data : [1,0,1,0,4,8,0,1,1,0,0,0,0,0,0],
        color : '#000000'
      }
    ]
  });});
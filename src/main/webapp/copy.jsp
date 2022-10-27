<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>颜值测试pk排行榜</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />

    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery-1-4-2.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="js/showhide.js"></script>
    <script type="text/JavaScript" src="js/jquery.mousewheel.js"></script>

    <link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css" />
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/ddsmoothmenu.js"></script>

    <script type="text/javascript">
        ddsmoothmenu.init({
            mainmenuid: "templatemo_menu", //menu DIV id
            orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
            classname: 'ddsmoothmenu', //class added to menu's outer DIV
            //customtheme: ["#1c5a80", "#18374a"],
            contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
        })

    </script>
    <!-- Load the CloudCarousel JavaScript file -->
    <script type="text/JavaScript" src="js/cloud-carousel.1.0.5.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){

            // This initialises carousels on the container elements specified, in this case, carousel1.
            $("#carousel1").CloudCarousel(
                {
                    reflHeight: 40,
                    reflGap: 2,
                    titleBox: $('#da-vinci-title'),
                    altBox: $('#da-vinci-alt'),
                    buttonLeft: $('#slider-left-but'),
                    buttonRight: $('#slider-right-but'),
                    yRadius: 30,
                    xPos: 480,
                    yPos: 32,
                    speed:0.15,
                    autoRotate: "yes",
                    autoRotateDelay: 1500
                }
            );


            $("#addpic").click(function (){
                if(confirm("是否添加数据???")){
                    window.location.href="addpic";
                }
            });

            $(".deletepic").click(function (){

                // alert("删除");
                var value = $(this).parent().siblings("input").eq(0).val();
                var top= $(this).parent().siblings("input").eq(1).val();
                // alert(value);
                if(confirm("确定删除Top"+top+" ???")){
                    window.location.href="delete.action?id="+value;
                }

            });

            $(".updatepic").click(function (){
                var top= "Top"+$(this).parent().siblings("input").eq(1).val();
                // $(this).siblings().find('h4[id="myModalLabel"]').html(top);

                $('h4[id="myModalLabel"]').html(top);

                var id=$(this).parent().siblings("input").eq(0).val();
                // var age=$(this).parent().siblings("span").eq(0).html();
                // var  beauty=$(this).parent().siblings("span").eq(1).html();
                // var  gender=$(this).parent().siblings("span").eq(2).html();
                // var  glasses=$(this).parent().siblings("span").eq(3).html();
                // var expression=$(this).parent().siblings("span").eq(4).html();

                // console.log(id+"*",typeof(id));
                // console.log(age+"*",typeof(age));
                // console.log(beauty+"*",typeof(beauty));
                // console.log(gender+"*",typeof(gender));
                // console.log(glasses+"*",typeof(glasses));
                // console.log(expression+"*",typeof(expression));


                $.ajax({
                    type : "GET",  //提交方式
                    url : "${pageContext.request.contextPath}/getFaceById",//路径
                    data : {
                        "id" : id
                    },//数据，这里使用的是Json格式进行传输
                    success : function(result) {//返回数据根据结果进行相应的处理
                        console.log(result);
                        $('input[id="up_id"]').val(result.id);
                        $('input[id="up_age"]').val(result.age);
                        $('input[id="up_beauty"]').val(result.beauty.toFixed(1));

                        if (result.gender=="male"){
                            $('input[id="up_man"]').attr("checked", true);
                        }else {
                            $('input[id="up_female"]').attr("checked",true);
                        }

                        if(result.glasses=="common"){
                            $('input[id="up_glasses"]').attr("checked", true);
                        }else {
                            $('input[id="up_glass"]').attr("checked", true);
                        }

                        if(result.expression=="smile"){
                            $('input[id="up_expresses"]').attr("checked", true);
                        }else {
                            $('input[id="up_express"]').attr("checked", true);
                        }


                    }
                });

            });

            $("button[id='up_sumbit']").click(function (){
                var up_id = $('input[id="up_id"]').val();
                var up_age  =$('input[id="up_age"]').val();
                var up_beauty=$('input[id="up_beauty"]').val();
                var up_gender=null;
                var up_glasses=null;
                var up_expression=null;


                if( $('input[id="up_man"]').attr("checked")==true){
                    up_gender="male";
                }else {
                    up_gender="female";
                }
                if( $('input[id="up_glasses"]').attr("checked")==true){
                    up_glasses="common";
                }else {
                    up_glasses="none";
                }

                if( $('input[id="up_expresses"]').attr("checked")==true){
                    up_expression="smile";
                }else {
                    up_expression="none";
                }
                $.ajax({
                    type : "POST",  //提交方式
                    url : "${pageContext.request.contextPath}/update.action",//路径
                    data : {
                        "id" : up_id,
                        "age":up_age,
                        "beauty":up_beauty,
                        "gender":up_gender,
                        "glasses":up_glasses,
                        "expression":up_expression
                    },
                    //数据，这里使用的是Json格式进行传输
                    success : function(result) {//返回数据根据结果进行相应的处理
                        if (result>0){
                            console.log(result);
                            window.location.href="query.action";
                        }
                    }
                });


                // alert(up_id+" || "+up_age+" || "+up_beauty+" || "+up_gender +" || "+up_glasses+" || "+up_expression);
            });


        });



    </script>

</head>

<body id="home">

<div id="templatemo_header_wrapper">
    <div id="site_title">
        <h1><a href="#">颜值排行榜</a></h1>
        <button id="addpic" class="btn btn-success" style="position:relative;left: 850px">添加</button>
    </div>

    <div id="templatemo_menu" class="ddsmoothmenu">
        <ul>
        </ul>
        <!-- <br style="clear: left" /> -->
    </div> <!-- end of templatemo_menu -->
    <div class="cleaner"></div>
</div>	<!-- END of templatemo_header_wrapper -->

<!-- 轮播图 -->
<div id="templatemo_slider">
    <!-- This is the container for the carousel. -->
    <div id = "carousel1" style="width:1000px; height:460px;background:none;/*overflow:scroll;*/ margin-left: 0px; margin-top: 20px">
        <c:forEach items="${faces }" var="face" varStatus="top">
            <a href="#" rel="lightbox"><img class="cloudcarousel" src="${face.imgPath}" alt="CSS Templates 2" title="Website Templates 2" /></a>
        </c:forEach>
    </div>

    <!-- Define left and right buttons. -->
    <center>
        <input id="slider-left-but" type="button" value="" />
        <input id="slider-right-but" type="button" value="" />
    </center>
</div>
<div id="templatemo_main">
    <div class="col one_third fp_services">
        <h2>颜值排行榜</h2>
        <c:forEach items="${faces }" var="face" varStatus="top">
            Top${top.index+1}
            <div class="rp_pp">
                <img src="${face.imgPath}" alt="Image ${top.index}" style="height: 160px;width: 160px;"/>
                <p>人脸检测数据</p>
                <p>
                    <input type="hidden" name="id" value="${face.id}">
                    <input type="hidden" name="top" value="${top.index+1}">
                    年龄：<span>${face.age}</span><br/>
                    颜值：<span>${face.beauty}</span><br/>
                    性别：<span>${face.gender=="male"?"男":"女"}</span><br/>
                    <!-- 获取是否带眼睛 none:无眼镜，common:普通眼镜，sun:墨镜 -->
                    是否戴眼镜:<span>${face.glasses=="none"?"无眼镜":face.glasses=="common"?"普通眼镜":"墨镜"}</span><br/>
                    <!-- 获取是否微笑，none:不笑；smile:微笑；laugh:大笑 -->
                    表情：<span>${face.expression=="none"?"不笑":face.expression=="smile"?"微笑":"大笑"}</span><br/>



                    <span>
						     <button class="deletepic btn bg-danger">删除</button>
                              <%--测试--%>
                        <!-- 按钮触发模态框 -->
                     <button class="updatepic btn btn-warning" data-toggle="modal" data-target="#myModal">编辑</button>
                  </span>
                    <!-- 模态框（Modal） -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
                            </div>
                            <div class="modal-body">

                                <form class="form-horizontal" role="form">
                                    <input type="hidden" id="up_id">
                                    <div class="form-group">
                                        <label for="up_age" class="col-sm-2 control-label">年龄</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="up_age"
                                                   placeholder="请输入更新之后的年龄">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="up_beauty" class="col-sm-2 control-label">颜值</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="up_beauty"
                                                   placeholder="请输入更新之后的颜值">
                                        </div>
                                    </div>

                                    <div class="form-group">

                                        <div class="col-sm-offset-2 col-sm-10">
                                            <div>
                                                <label class="radio-inline">
                                                    <input type="radio" name="up_sex" id="up_man" value="male" >男
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="up_sex" id="up_female"  value="female">女
                                                </label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">

                                        <div class="col-sm-offset-2 col-sm-10">
                                            <div>
                                                <label class="radio-inline">
                                                    <input type="radio" name="up_glasses" id="up_glasses" value="common" >有眼镜
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="up_glasses" id="up_glass"  value="none">无眼镜
                                                </label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">

                                        <div class="col-sm-offset-2 col-sm-10">
                                            <div>
                                                <label class="radio-inline">
                                                    <input type="radio" name="up_expresses" id="up_expresses" value="smile" >笑
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="up_expresses" id="up_express"  value="none">不笑
                                                </label>
                                            </div>
                                        </div>
                                    </div>




                                </form>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button id="up_sumbit" type="button" class="btn btn-primary"data-dismiss="modal">提交更改</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>

                    <%--这里是测试--%>

                </p>
                <div class="cleaner"></div>
            </div>
        </c:forEach>
    </div>
    <div class="cleaner"></div>
</div>
</div> <!-- END of templatemo_footer -->
</body>
</html>
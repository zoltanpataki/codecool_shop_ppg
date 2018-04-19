(function ($) {
    "use strict";

    /*==================================================================
    [ Focus input ]*/
    $('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() !== "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })    
    });
  
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) === false){
                showValidate(input[i]);
                check=false;
            }
        }
        return check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') === 'email' || $(input).attr('name') === 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) === null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() === ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    /*==================================================================
    [ Show pass ]*/
    var showPass = 0;
    $('.btn-show-pass').on('click', function(){
        if(showPass === 0) {
            $(this).next('input').attr('type','text');
            $(this).addClass('active');
            showPass = 1;
        }
        else {
            $(this).next('input').attr('type','password');
            $(this).removeClass('active');
            showPass = 0;
        }
        
    });

    $(document).ready(function(){
        $("#username").change(function(){
            $("#message").html("<p></p> checking...");
            var username = $("#username").val();
            $.ajax({
                type:"get",
                url:"/username",
                data:{"username":username},
                success:function(data){
                    if (JSON.parse(data).username_exist) {
                        $("#message").html('<span style="font-size:13px; color: red"> Username already taken</span>');
                    }
                    else {
                        $("#message").html('<span style="font-size:13px; color: black"> Username available</span>');
                    }
                }
            });

        });

    });

    $(document).ready(function(){
        $("#email").change(function(){
            $("#message2").html("<p></p> checking...");
            var email = $("#email").val();
            $.ajax({
                type:"get",
                url:"/email",
                data:{"email":email},
                success:function(data){
                    if (JSON.parse(data).email_exist) {
                        $("#message2").html("<span style=font-size:13px; color: red'> Email address already taken</span>");
                    }
                    else {
                        $("#message2").html("<span style='font-size:13px; color: black'> Email address available</span>");
                    }
                }
            });

        });

    });

})(jQuery);
package com.xmwdkk.boothprint.printutil;

import android.content.Context;
import android.util.Log;

import com.xmwdkk.boothprint.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * 测试数据生成器
 * Created by liuguirong on 8/1/17.
 */

public class PrintOrderDataMaker implements PrintDataMaker {


    private String qr;
    private int width;
    private int height;
    Context btService;
    private String remark = "微点筷客推出了餐厅管理系统，可用手机快速接单（来自客户的预订订单），进行订单管理、后厨管理等管理餐厅。";


    public PrintOrderDataMaker( Context btService, String qr, int width, int height) {
        this.qr = qr;
        this.width = width;
        this.height = height;
        this.btService = btService;
    }


    //mod by xj start
    @Override
    public List<byte[]> getPrintData(int type) {

        //List<byte[]> data = constructDataDemo(type);
        List<byte[]> data = constructDataDemoxj(type);
        return data;
    }

    public List<byte[]> getPrintData(int type, String jsondata) {
        List<byte[]> data = constructDataDemoxj(type, jsondata);
        return data;
    }

    private List<byte[]> constructDataDemo(int type) {
        ArrayList<byte[]> data = new ArrayList<>();

        try {
            PrinterWriter printer;
            printer = type == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(height, width) : new PrinterWriter80mm(height, width);
            printer.setAlignCenter();
            data.add(printer.getDataAndReset());

            ArrayList<byte[]> image1 = printer.getImageByte(btService.getResources(), R.drawable.demo);

            data.addAll(image1);

            printer.setAlignLeft();
            printer.printLine();
            printer.printLineFeed();

            printer.printLineFeed();
            printer.setAlignCenter();
            printer.setEmphasizedOn();
            printer.setFontSize(1);
            printer.print("好吃点你就多吃点");
            printer.printLineFeed();
            printer.setEmphasizedOff();
            printer.printLineFeed();

            printer.printLineFeed();
            printer.setFontSize(0);
            printer.setAlignCenter();
            printer.print("订单编号：" + "546545645465456454");
            printer.printLineFeed();

            printer.setAlignCenter();
            printer.print(new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                    .format(new Date(System.currentTimeMillis())));
            printer.printLineFeed();
            printer.printLine();

            printer.printLineFeed();
            printer.setAlignLeft();
            printer.print("订单状态: " + "已接单");
            printer.printLineFeed();
            printer.print("用户昵称: " +"周末先生");
            printer.printLineFeed();
            printer.print("用餐人数: " + "10人");
            printer.printLineFeed();
            printer.print("用餐桌号:" + "A3" + "号桌");
            printer.printLineFeed();
            printer.print("预定时间：" + "2017-10-1 17：00");
            printer.printLineFeed();
            printer.print("预留时间：30分钟");
            printer.printLineFeed();
            printer.print("联系方式：" + "18094111545454");
            printer.printLineFeed();
            printer.printLine();
            printer.printLineFeed();

            printer.setAlignLeft();
            printer.print("备注：" + "记得留位置");
            printer.printLineFeed();
            printer.printLine();

            printer.printLineFeed();

            printer.setAlignCenter();
            printer.print("菜品信息");
            printer.printLineFeed();
            printer.setAlignCenter();
            printer.printInOneLine("菜名", "数量", "单价", 0);
            printer.printLineFeed();
            for (int i = 0; i < 3; i++) {

                printer.printInOneLine("干锅包菜", "X" + 3, "￥" + 30, 0);
                printer.printLineFeed();
            }
            printer.printLineFeed();
            printer.printLine();
            printer.printLineFeed();
            printer.setAlignLeft();
            printer.printInOneLine("菜品总额：", "￥" + 100, 0);


            printer.setAlignLeft();
            printer.printInOneLine("优惠金额：", "￥" +"0.00"
                    , 0);
            printer.printLineFeed();

            printer.setAlignLeft();
            printer.printInOneLine("订金/退款：", "￥" + "0.00"
                    , 0);
            printer.printLineFeed();


            printer.setAlignLeft();
            printer.printInOneLine("总计金额：", "￥" +90, 0);
            printer.printLineFeed();

            printer.printLine();
            printer.printLineFeed();
            printer.setAlignCenter();
            printer.print("谢谢惠顾，欢迎再次光临！");
            printer.printLineFeed();
            printer.printLineFeed();
            printer.printLineFeed();
            printer.feedPaperCutPartial();

            data.add(printer.getDataAndClose());
            return data;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    //mod by xj end


    /**
     * Add bt xj -s
     */
    private List<byte[]> constructDataDemoxj(int type) {
        ArrayList<byte[]> data = new ArrayList<>();

        try {
            PrinterWriter printer;
            printer = type == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(height, width) : new PrinterWriter80mm(height, width);
            printer.setAlignCenter();
            data.add(printer.getDataAndReset());


            printer.setAlignLeft();
            printer.printLine();
            printer.printLineFeed();

            printer.printLineFeed();
            printer.setAlignCenter();
            printer.setEmphasizedOn();
            printer.setFontSize(1);
            printer.print("4号楼 王老板");
            printer.printLineFeed();
            printer.setEmphasizedOff();
            printer.printLineFeed();

            printer.printLineFeed();
            printer.setFontSize(0);
            printer.setAlignCenter();

            printer.printLineFeed();

            printer.setAlignCenter();
            printer.print(new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                    .format(new Date(System.currentTimeMillis())));
            printer.printLineFeed();
            printer.printLine();

            printer.setFontSize(1);
            printer.printLineFeed();
            printer.setAlignLeft();
            printer.print("一荤一素 " + "4份");
            printer.printLineFeed();
            printer.print("米饭  9 个 ");
            printer.printLineFeed();
            printer.print("馒头  10个 ");
            printer.printLineFeed();
            printer.print("备注: 带汤");

            printer.printLineFeed();
            printer.print("联系方式：" + "18600971728");
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.printLine();
            printer.setAlignCenter();
            printer.print("谢谢惠顾，平安吉祥！");
            printer.printLineFeed();
            printer.printLineFeed();
            printer.printLineFeed();
            printer.feedPaperCutPartial();

            data.add(printer.getDataAndClose());
            return data;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<byte[]> constructDataDemoxj(int type, String jsonstr) {
        ArrayList<byte[]> data = new ArrayList<>();


        try {
            JSONObject jsondata = new JSONObject(jsonstr);
            String orderaddr = jsondata.getString("select_addr");
            String cate = jsondata.getString("select_category");
            String name = jsondata.getString("select_name");
            int food_num = jsondata.getInt("food_num");
            int rice_num = jsondata.getInt("rice_num");
            int mantou_num = jsondata.getInt("mantou_num");

            PrinterWriter printer;
            printer = type == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(height, width) : new PrinterWriter80mm(height, width);
            printer.setAlignCenter();
            data.add(printer.getDataAndReset());


            printer.setAlignLeft();
            printer.printLine();
            printer.printLineFeed();

            printer.printLineFeed();
            printer.setAlignCenter();
            printer.setEmphasizedOn();
            printer.setFontSize(1);
            printer.print(orderaddr + " " + name);
            printer.printLineFeed();
            printer.setEmphasizedOff();
            printer.printLineFeed();

            printer.printLineFeed();
            printer.setFontSize(0);
            printer.setAlignCenter();

            printer.printLineFeed();

            printer.setAlignCenter();
            printer.print(new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                    .format(new Date(System.currentTimeMillis())));
            printer.printLineFeed();
            printer.printLine();

            printer.setFontSize(1);
            printer.printLineFeed();
            printer.setAlignLeft();
            printer.print(cate + food_num + "份");
            printer.printLineFeed();
            printer.print("米饭  " + rice_num + "份");
            printer.printLineFeed();
            printer.print("馒头  " + mantou_num + "个");
            printer.printLineFeed();

            printer.printLineFeed();
            printer.print("联系方式：" + "17301327587");
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.printLine();
            printer.setAlignCenter();
            printer.print("谢谢惠顾，平安吉祥！");
            printer.printLineFeed();
            printer.printLineFeed();
            printer.printLineFeed();
            printer.feedPaperCutPartial();

            data.add(printer.getDataAndClose());
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    /**
     * Add bt xj -s
     */

}

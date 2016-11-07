package com.example.asocket;

public class ExREG { //存什么类型
	public byte  TypeL;
	public byte  TypeH;
    public byte  LenL;
    public byte  LenH;
   // public byte   clientID;   //java中一个char占2个字节
    public String   clientName;
    public byte  Endflag;
      
    public ExREG(byte typel, byte typeh, byte lenl, byte lenh, String clientname, byte endflag)//对应每一个元素,增加构造函数,初始化：A a = new A();自动分配内存
    //http://zhidao.baidu.com/link?url=AB6Lton3JPEI8TtmjMHpLXZUOr_bt0E247beV77T8IV7r1Km61hq3JwNPZDQktnj8pisxrcsBVdOePsMkc-MrEqp7SWK5zZ5WWkgsS-6zYq
    {
        this.TypeL       = typel;
        this.TypeH       = typeh;
        this.LenL      = lenl;
        this.LenH      = lenh;
   //     this.clientID   = clientid;   
        this.clientName = clientname;
        this.Endflag =endflag;
        
 }
}

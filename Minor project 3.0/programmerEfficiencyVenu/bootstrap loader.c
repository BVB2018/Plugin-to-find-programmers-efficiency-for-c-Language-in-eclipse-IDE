char b[9];
void padding(int ch1,int ch2){
    int temp=0,i=0;
    ch1=ch1<<4;
    temp=ch1|ch2;
    b[0] = '\0';
    int z=256;
    for(i=0;i<8;i++){
        z=z>>1;
        strcat(b, ((temp & z) == z) ? "1" : "0");
    }
   // printf("ch1:%d ch2:%d b:%s bd:%d\n",ch1,ch2,b,b);
}
int convert(char address[]){
    int i=0,val=0,value=0;
    char op;
    for(i=0;i<6;i++){
        op=address[i];
        if((op>='A')&&(op<='F')){
            value=op-55;
        }
        if((op>='0')&&(op<='9')){
            value=op-'0';
        }
        val=val+(value*(pow(16,(5-i))));
    }
    return val;
}
int main()
{
    FILE *fp_ob_prog=NULL;
    char op,ch,start_add[20],len[20];
    int cnt=0,val=0,ch1,ch2,c,start_address=0,length=0,memory=500;
    fp_ob_prog=fopen("C:/Users/USER/Documents/project/ss2/object_prog.txt","r");
    if(fp_ob_prog==NULL){
        printf("err in opening object prog");
    }
    else{
        while(!feof(fp_ob_prog)){
            ch=op;
            op=fgetc(fp_ob_prog);
            if(op=='H'){
                cnt=0;
                while(op!='\n'){
                    op=fgetc(fp_ob_prog);
                    if(op=='^'){
                        cnt++;
                    }
                    if(cnt==3){
                        len[0]='\0';
                        for(c=0;c<6;c++){
                              op=fgetc(fp_ob_prog);
                              len[c]=op;
                        }
                        len[c]='\0';
                        length=convert(len);
                        //printf("len:%d\n",length);
                        if(length>memory){
                            printf("\nInsufficient space to load the given object code\n");
                            exit(0);
                        }
                        else if(length==0){
                            printf("\nThere is nothing to load\n");
                            exit(0);
                        }
                        else{
                            op=fgetc(fp_ob_prog);
                        }
                    }
                }
            }
            if((op=='T')&&(ch=='\n')){
                val=0;
                cnt=0;
                while(op!='\n'){
                    op=fgetc(fp_ob_prog);
                    if(op=='^'){
                        cnt++;
                    }
                    if(cnt==1){
                        start_add[0]='\0';
                        for(c=0;c<6;c++){
                              op=fgetc(fp_ob_prog);
                              start_add[c]=op;
                        }
                        start_add[c]='\0';
                        start_address=convert(start_add);
                    }
                    if(cnt>=3){
                       if(op!='^'){
                        val++;
                        if((op>='A')&&(op<='F')){
                            op=op-23;
                        }
                        if((op>='0')&&(op<='9')){
                            op=op-'0';
                        }
                       }
                       if(val==1){
                        ch1=op;
                       }
                       else if(val==2){
                        val=0;
                        ch2=op;
                        //printf("ch1:%d  ch2:%d\n",ch1,ch2);
                        padding(ch1,ch2);
                        printf("%X\t%s\n",start_address,b);
                        start_address++;
                       }
                    }
                }
            }
        }
    }
    fclose(fp_ob_prog);
    return 0;
}

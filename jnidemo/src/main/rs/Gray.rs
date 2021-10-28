
#pragma version(1)
#pragma rs java_package_name(cn.ololee.jnidemo.rs)

//uchar4 pinyinData;
rs_allocation pinyinData;


uchar __attribute__((kernel)) strong(uchar range){
    uchar result = range;
    if(range>127){
        if(range<192){
            uchar inc = range-127;
            result = range + inc*0.1f;
        }else{
            uchar incs = range-192;
            result = range + incs*0.1f;
        }
    }
    return result;
}


uchar4 __attribute__((kernel)) idontknow(uchar4 in, uint32_t x, uint32_t y) {
    uchar4 out = in;

   out.r=strong(in.r);
   out.g=strong(in.g);
   out.b=strong(in.b);

    return out;
}

uchar4 __attribute__((kernel)) blackGold(uchar4 in, uint32_t x, uint32_t y) {
    uchar4 out = in;

    if ((in.r < in.b) && (in.g < in.b)) {
        out.r = out.g = out.b = (in.r*299 + in.g*587 + in.b*114 + 500) / 1000;
    }

    return out;
}


uchar4 __attribute__((kernel)) pinyin(uchar4 query, uint32_t x, uint32_t y) {
   // uchar4 chinese=rsAllocationVLoadX_uchar4(pinyinData,x,y);
    uchar4 out={};
    return out;
}

void root(const uchar4 *in, uchar4 *out, uint32_t x, uint32_t y) {

    out->a = in->a;

    //out->r = out->g = out->b = (in->r + in->g + in->b) / 3;


    // out->r = out->g = out->b = (in->r * 299 + in->g * 587 + in->b * 114 + 500) / 1000;
    out->r = 255-in->r;
    out->g = 255-in->g;
    out->b = 255-in->b;
}



void init() {
}


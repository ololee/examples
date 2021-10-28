package cn.ololee.jnidemo;

import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.FieldPacker;
import androidx.renderscript.RSRuntimeException;
import androidx.renderscript.RenderScript;
import androidx.renderscript.Script;
import androidx.renderscript.ScriptC;
import androidx.renderscript.Type;
import cn.ololee.jnidemo.rs.GrayBitCode;

public class ScriptGray extends ScriptC {
  private static final String __rs_resource_name = "gray";
  // Constructor
  public  ScriptGray(RenderScript rs) {
    super(rs,
        __rs_resource_name,
        GrayBitCode.getBitCode32(),
        GrayBitCode.getBitCode64());
    //short []part1 = new short[]{1};
    //short []part2 = new short[]{1};
    //short []part3 = new short[]{1};
    //mExportVar_pinyindata = new short[21000];

    __I16 = Element.I16(rs);
    __U8_4 = Element.U8_4(rs);
  }

  private Element __I16;
  private Element __U8_4;
  private FieldPacker __rs_fp_I16;
  private final static int mExportVarIdx_pinyindata = 0;
  private short[] mExportVar_pinyindata;
  public synchronized void set_pinyindata(short[] v) {
    mExportVar_pinyindata = v;
    FieldPacker fp = new FieldPacker(42000);
    for (int ct1 = 0; ct1 < 21000; ct1++) {
      fp.addI16(v[ct1]);
    }

    int []__dimArr = new int[1];
    __dimArr[0] = 21000;
    setVar(mExportVarIdx_pinyindata, fp, __I16, __dimArr);
  }

  public short[] get_pinyindata() {
    return mExportVar_pinyindata;
  }

  public Script.FieldID getFieldID_pinyindata() {
    return createFieldID(mExportVarIdx_pinyindata, null);
  }

  private final static int mExportForEachIdx_root = 0;
  public Script.KernelID getKernelID_root() {
    return createKernelID(mExportForEachIdx_root, 27, null, null);
  }

  public void forEach_root(Allocation ain, Allocation aout) {
    forEach_root(ain, aout, null);
  }

  public void forEach_root(Allocation ain, Allocation aout, Script.LaunchOptions sc) {
    // check ain
    if (!ain.getType().getElement().isCompatible(__U8_4)) {
      throw new RSRuntimeException("Type mismatch with U8_4!");
    }
    // check aout
    if (!aout.getType().getElement().isCompatible(__U8_4)) {
      throw new RSRuntimeException("Type mismatch with U8_4!");
    }
    Type t0, t1;        // Verify dimensions
    t0 = ain.getType();
    t1 = aout.getType();
    if ((t0.getCount() != t1.getCount()) ||
        (t0.getX() != t1.getX()) ||
        (t0.getY() != t1.getY()) ||
        (t0.getZ() != t1.getZ()) ||
        (t0.hasFaces()   != t1.hasFaces()) ||
        (t0.hasMipmaps() != t1.hasMipmaps())) {
      throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
    }

    forEach(mExportForEachIdx_root, ain, aout, null, sc);
  }

  private final static int mExportForEachIdx_blackGold = 1;
  public Script.KernelID getKernelID_blackGold() {
    return createKernelID(mExportForEachIdx_blackGold, 59, null, null);
  }

  public void forEach_blackGold(Allocation ain, Allocation aout) {
    forEach_blackGold(ain, aout, null);
  }

  public void forEach_blackGold(Allocation ain, Allocation aout, Script.LaunchOptions sc) {
    // check ain
    if (!ain.getType().getElement().isCompatible(__U8_4)) {
      throw new RSRuntimeException("Type mismatch with U8_4!");
    }
    // check aout
    if (!aout.getType().getElement().isCompatible(__U8_4)) {
      throw new RSRuntimeException("Type mismatch with U8_4!");
    }
    Type t0, t1;        // Verify dimensions
    t0 = ain.getType();
    t1 = aout.getType();
    if ((t0.getCount() != t1.getCount()) ||
        (t0.getX() != t1.getX()) ||
        (t0.getY() != t1.getY()) ||
        (t0.getZ() != t1.getZ()) ||
        (t0.hasFaces()   != t1.hasFaces()) ||
        (t0.hasMipmaps() != t1.hasMipmaps())) {
      throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
    }

    forEach(mExportForEachIdx_blackGold, ain, aout, null, sc);
  }

  private final static int mExportForEachIdx_pinyin = 2;
  public Script.KernelID getKernelID_pinyin() {
    return createKernelID(mExportForEachIdx_pinyin, 59, null, null);
  }

  public void forEach_pinyin(Allocation ain_in, Allocation ain_diff, Allocation aout) {
    forEach_pinyin(ain_in, ain_diff, aout, null);
  }

  public void forEach_pinyin(Allocation ain_in, Allocation ain_diff, Allocation aout, Script.LaunchOptions sc) {
    // check ain_in
    if (!ain_in.getType().getElement().isCompatible(__U8_4)) {
      throw new RSRuntimeException("Type mismatch with U8_4!");
    }
    // check ain_diff
    if (!ain_diff.getType().getElement().isCompatible(__U8_4)) {
      throw new RSRuntimeException("Type mismatch with U8_4!");
    }
    // check aout
    if (!aout.getType().getElement().isCompatible(__U8_4)) {
      throw new RSRuntimeException("Type mismatch with U8_4!");
    }
    Type t0, t1;        // Verify dimensions
    t0 = ain_in.getType();
    t1 = ain_diff.getType();
    if ((t0.getCount() != t1.getCount()) ||
        (t0.getX() != t1.getX()) ||
        (t0.getY() != t1.getY()) ||
        (t0.getZ() != t1.getZ()) ||
        (t0.hasFaces()   != t1.hasFaces()) ||
        (t0.hasMipmaps() != t1.hasMipmaps())) {
      throw new RSRuntimeException("Dimension mismatch between parameters ain_in and ain_diff!");
    }

    // Verify dimensions
    t0 = ain_in.getType();
    t1 = aout.getType();
    if ((t0.getCount() != t1.getCount()) ||
        (t0.getX() != t1.getX()) ||
        (t0.getY() != t1.getY()) ||
        (t0.getZ() != t1.getZ()) ||
        (t0.hasFaces()   != t1.hasFaces()) ||
        (t0.hasMipmaps() != t1.hasMipmaps())) {
      throw new RSRuntimeException("Dimension mismatch between parameters ain_in and aout!");
    }

    forEach(mExportForEachIdx_pinyin, new Allocation[]{ain_in, ain_diff}, aout, null, sc);
  }

}


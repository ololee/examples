package cn.ololee.jnidemo.particle;

public class Particle {
  private final static int POSITION_COMPONENT_COUNT = 3;
  private final static int COLOR_COMPONENT_COUNT = 3;
  private final static int VECTOR_COMPONENT_COUNT = 3;
  private final static int PARTICLE_START_TIME_COMPONENT_COUNT = 1;

  private final static int TOTAL_COMPONENT_COUNT = POSITION_COMPONENT_COUNT +
      COLOR_COMPONENT_COUNT +
      VECTOR_COMPONENT_COUNT +
      PARTICLE_START_TIME_COMPONENT_COUNT;

  private final static int BYTES_PER_FLOAT = 4;
  private final static int STRIDE = TOTAL_COMPONENT_COUNT * BYTES_PER_FLOAT;

  private int maxParticleCount, currentParticleCount, nextParticle;
  private float[] particles;

  public Particle(int maxParticleCount) {
    this.maxParticleCount = maxParticleCount;
    particles = new float[TOTAL_COMPONENT_COUNT * maxParticleCount];
  }

  public void addParticle(){

  }
}

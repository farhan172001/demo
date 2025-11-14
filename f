@Configuration
@Scope("singleton")
public class MQQueuePool extends ObjectPool<MQQueue> {

    @Autowired
    private Environment env;

    private static MQQueueManager mqQueueManager = null;

    private String mqHost;
    private String mqPort;
    private String mqChannel;
    private String mqQueueName;
    private String mqManagerName;

    @PostConstruct
    private void initializeMQQueuePool() {
        try {
            if (mqQueueManager == null || !mqQueueManager.isConnected()) {

                this.mqHost = env.getProperty("mq.host");
                this.mqPort = env.getProperty("mq.port");
                this.mqChannel = env.getProperty("mq.channel");
                this.mqQueueName = env.getProperty("mq.queueName");
                this.mqManagerName = env.getProperty("mq.managerName");

                MQEnvironment.hostname = this.mqHost;
                MQEnvironment.port = Integer.parseInt(this.mqPort);
                MQEnvironment.channel = this.mqChannel;

                mqQueueManager = new MQQueueManager(mqManagerName);
            }
        } catch (Exception ex) {
            mqQueueManager = null;
            throw new RuntimeException(ex);
        }
    }

    @Override
    public MQQueue create() {
        try {
            if (mqQueueManager == null || !mqQueueManager.isConnected()) {
                initializeMQQueuePool();
            }

            int openOptions = MQC.MQOO_INPUT_SHARED + MQC.MQOO_FAIL_IF_QUIESCING;
            return mqQueueManager.accessQueue(mqQueueName, openOptions);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void expire(MQQueue o) {
        try {
            o.close();
        } catch (Exception ignored) {}
    }

    @Override
    public boolean validate(MQQueue o) {
        return o != null && o.isOpen();
    }
}

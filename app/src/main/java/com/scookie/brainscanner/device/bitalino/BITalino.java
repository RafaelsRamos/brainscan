package com.scookie.brainscanner.device.bitalino;

public enum BITalino implements IDevice<BitCommandProperties> {

    START {
        @Override
        public BitCommandProperties getCommand(BitCommandArguments arguments) {
            int[] bitalinoChannels = arguments.getAnalogChannels();
            int bit = 1;
            for (int channel : bitalinoChannels)
                bit = bit | 1 << (2 + channel);

            byte[] cmd = {(byte)bit};

            return new BitCommandProperties(cmd, 1);
        }
    },
    SET_FREQ {
        @Override
        public BitCommandProperties getCommand(BitCommandArguments arguments) {
            int bitalinoFreq = arguments.getSampleRate();
            byte[] freqCommand = new byte[1];
            switch(bitalinoFreq){
                case 1:
                    freqCommand[0] = (byte)0x03;
                    break;
                case 10:
                    freqCommand[0] = (byte)0x43;
                    break;
                case 100:
                    freqCommand[0] = (byte)0x83;
                    break;
                case 1000:
                    freqCommand[0] = (byte)0xC3;
                    break;
            }

            return new BitCommandProperties(freqCommand, 1);
        }
    },
    STOP {
        @Override
        public BitCommandProperties getCommand(BitCommandArguments arguments) {
            byte[] endCommand = {(byte)0x00};
            return new BitCommandProperties(endCommand, 1);
        }
    }, VERSION {
        @Override
        public BitCommandProperties getCommand(BitCommandArguments argument) {
            boolean isBLE = argument.isBLE();

            byte[] descriptionCommand = {(byte)0x07}; //BTH
            //byte[] descriptionCommand = {(byte)0x0F}; //BLE
            return new BitCommandProperties(descriptionCommand, 1);
        }
    }, TRIGGER{
        @Override
        public BitCommandProperties getCommand(BitCommandArguments arguments) {
            boolean isBITalino2 = arguments.isBitalino2();
            int[] bitalinoDigitalChannels = arguments.getDigitalChannels();
            byte data;

            if(isBITalino2){
                data = (byte) 0xB3;
            }
            else{
                data = (byte) 0x03;
            }

            int i = 0;
            for (int digitalChannel: bitalinoDigitalChannels) {
//                data |= (byte)(0x04 << i);
                data |= (byte)(digitalChannel<<(2+i));
                i++;
            }

            byte[] triggerCommand = new byte[]{data};
            return new BitCommandProperties(triggerCommand, triggerCommand.length);

        }
    }, STATE {
        @Override
        public BitCommandProperties getCommand(BitCommandArguments argument) {
            byte[] command = {(byte)0x0B}; //BTH

            return new BitCommandProperties(command, 1);
        }
    }, PWM {
        @Override
        public BitCommandProperties getCommand(BitCommandArguments argument) {
            byte[] command = {(byte) 0xA3};

            return new BitCommandProperties(command, 1);
        }
    }, BATTERY {
        @Override
        public BitCommandProperties getCommand(BitCommandArguments argument) {
            int batteryThreshold = argument.getBatteryThreshold();

            byte[] command = {(byte)(batteryThreshold << 2)};

            return new BitCommandProperties(command, 1);
        }
    }
}
package nnsp.vo;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class NcSysLoad implements Serializable {
	double cpuLoad;
	double ramLoad;
	double diskLoad;
	double netLoad;
	
	public double getCpuLoad() {
		return cpuLoad;
	}
	public void setCpuLoad(double cpuLoad) {
		this.cpuLoad = cpuLoad;
	}
	public double getRamLoad() {
		return ramLoad;
	}
	public void setRamLoad(double ramLoad) {
		this.ramLoad = ramLoad;
	}
	public double getDiskLoad() {
		return diskLoad;
	}
	public void setDiskLoad(double diskLoad) {
		this.diskLoad = diskLoad;
	}
	public double getNetLoad() {
		return netLoad;
	}
	public void setNetLoad(double netLoad) {
		this.netLoad = netLoad;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof NcSysLoad)) {
			return false;
		}
		NcSysLoad ncSysLoad = (NcSysLoad) o;
		return Double.compare(ncSysLoad.getCpuLoad(), getCpuLoad()) == 0
		&& Double.compare(ncSysLoad.getRamLoad(), getRamLoad()) == 0
		&& Double.compare(ncSysLoad.getDiskLoad(), getDiskLoad()) == 0;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getCpuLoad(), getRamLoad(), getDiskLoad());
	}

	@Override
	public String toString() {
		return "NdSysLoad{" +
			"cpuLoad=" + cpuLoad +
			", memLoad=" + ramLoad +
			", diskLoad=" + diskLoad + '}';
	}
}

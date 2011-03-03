package net.cellar.features;

import java.io.Serializable;

/**
 * @author iocanel
 */
public class FeatureInfo implements Serializable {

	private String name;
	private String version;

	public FeatureInfo(String name, String version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FeatureInfo info = (FeatureInfo) o;

		if (name != null ? !name.equals(info.name) : info.name != null) return false;
		if (version != null ? !version.equals(info.version) : info.version != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (version != null ? version.hashCode() : 0);
		return result;
	}
}

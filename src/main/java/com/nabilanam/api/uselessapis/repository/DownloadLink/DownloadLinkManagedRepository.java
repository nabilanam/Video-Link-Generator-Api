package com.nabilanam.api.uselessapis.repository.DownloadLink;

import com.nabilanam.api.uselessapis.model.ApiHost;
import com.nabilanam.api.uselessapis.model.downloadlink.DownloadLinkManaged;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadLinkManagedRepository extends JpaRepository<DownloadLinkManaged, Integer> {
	public DownloadLinkManaged findByApiHost(ApiHost apiHost);
}

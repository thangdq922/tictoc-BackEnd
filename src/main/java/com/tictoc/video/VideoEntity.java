package com.tictoc.video;

import java.util.List;

import com.tictoc.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "videos")
@Getter
@Setter
public class VideoEntity extends BaseEntity  {
	
	private String caption;
	
	private String fileUrl;
		
	private String music;
	
	private Long viewsCount = (long) 0;

	private List<String> allows;

}

/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.kubernetes.client.config.applications.single_source_multiple_files;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author wind57
 */
@ActiveProfiles("color")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = SingleSourceMultipleFilesApp.class,
		properties = { "spring.cloud.bootstrap.name=single-source-multiple-files",
				"spring.main.cloud-platform=KUBERNETES", "spring.cloud.bootstrap.enabled=true",
				"spring.cloud.kubernetes.client.namespace=spring-k8s", "single.source.multiple.files.stub=true" })
class SingleSourceMultipleFilesBootstrapTests extends SingleSourceMultipleFilesTests {

}

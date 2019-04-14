import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ExperienceInformationAttachmentComponent,
    ExperienceInformationAttachmentDetailComponent,
    ExperienceInformationAttachmentUpdateComponent,
    ExperienceInformationAttachmentDeletePopupComponent,
    ExperienceInformationAttachmentDeleteDialogComponent,
    experienceInformationAttachmentRoute,
    experienceInformationAttachmentPopupRoute
} from './';
import { SoptorshiExperienceInformationModule } from 'app/entities/experience-information/experience-information.module';

const ENTITY_STATES = [...experienceInformationAttachmentRoute, ...experienceInformationAttachmentPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES), SoptorshiExperienceInformationModule],
    declarations: [
        ExperienceInformationAttachmentComponent,
        ExperienceInformationAttachmentDetailComponent,
        ExperienceInformationAttachmentUpdateComponent,
        ExperienceInformationAttachmentDeleteDialogComponent,
        ExperienceInformationAttachmentDeletePopupComponent
    ],
    entryComponents: [
        ExperienceInformationAttachmentComponent,
        ExperienceInformationAttachmentUpdateComponent,
        ExperienceInformationAttachmentDeleteDialogComponent,
        ExperienceInformationAttachmentDeletePopupComponent
    ],
    exports: [ExperienceInformationAttachmentComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiExperienceInformationAttachmentModule {}

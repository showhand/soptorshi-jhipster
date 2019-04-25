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

const ENTITY_STATES = [...experienceInformationAttachmentRoute, ...experienceInformationAttachmentPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
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
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiExperienceInformationAttachmentModule {}

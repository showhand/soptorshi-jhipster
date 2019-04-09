import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AcademicInformationAttachmentComponent,
    AcademicInformationAttachmentDetailComponent,
    AcademicInformationAttachmentUpdateComponent,
    AcademicInformationAttachmentDeletePopupComponent,
    AcademicInformationAttachmentDeleteDialogComponent,
    academicInformationAttachmentRoute,
    academicInformationAttachmentPopupRoute
} from './';

const ENTITY_STATES = [...academicInformationAttachmentRoute, ...academicInformationAttachmentPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AcademicInformationAttachmentComponent,
        AcademicInformationAttachmentDetailComponent,
        AcademicInformationAttachmentUpdateComponent,
        AcademicInformationAttachmentDeleteDialogComponent,
        AcademicInformationAttachmentDeletePopupComponent
    ],
    entryComponents: [
        AcademicInformationAttachmentComponent,
        AcademicInformationAttachmentUpdateComponent,
        AcademicInformationAttachmentDeleteDialogComponent,
        AcademicInformationAttachmentDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAcademicInformationAttachmentModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AcademicInformationComponent,
    AcademicInformationDetailComponent,
    AcademicInformationUpdateComponent,
    AcademicInformationDeletePopupComponent,
    AcademicInformationDeleteDialogComponent,
    academicInformationRoute,
    academicInformationPopupRoute
} from './';
import { SoptorshiAcademicInformationAttachmentModule } from 'app/entities/academic-information-attachment/academic-information-attachment.module';

const ENTITY_STATES = [...academicInformationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, SoptorshiAcademicInformationAttachmentModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AcademicInformationComponent,
        AcademicInformationDetailComponent,
        AcademicInformationUpdateComponent,
        AcademicInformationDeleteDialogComponent,
        AcademicInformationDeletePopupComponent
    ],
    entryComponents: [
        AcademicInformationComponent,
        AcademicInformationUpdateComponent,
        AcademicInformationDeleteDialogComponent,
        AcademicInformationDeletePopupComponent
    ],
    exports: [
        AcademicInformationComponent,
        AcademicInformationDetailComponent,
        AcademicInformationUpdateComponent,
        AcademicInformationDeleteDialogComponent,
        AcademicInformationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAcademicInformationModule {}

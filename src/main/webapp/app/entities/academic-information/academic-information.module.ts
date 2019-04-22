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
import { EmployeeManagementModule } from 'app/employee-management/employee-management.module';

const ENTITY_STATES = [...academicInformationRoute, ...academicInformationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
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
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAcademicInformationModule {}

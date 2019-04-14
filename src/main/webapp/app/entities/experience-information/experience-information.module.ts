import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ExperienceInformationComponent,
    ExperienceInformationDetailComponent,
    ExperienceInformationUpdateComponent,
    ExperienceInformationDeletePopupComponent,
    ExperienceInformationDeleteDialogComponent,
    experienceInformationRoute,
    experienceInformationPopupRoute
} from './';
import { EmployeeComponent } from 'app/entities/employee';
import { SoptorshiFamilyInformationModule } from 'app/entities/family-information/family-information.module';

const ENTITY_STATES = [...experienceInformationRoute, ...experienceInformationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES), SoptorshiFamilyInformationModule],
    declarations: [
        ExperienceInformationComponent,
        ExperienceInformationDetailComponent,
        ExperienceInformationUpdateComponent,
        ExperienceInformationDeleteDialogComponent,
        ExperienceInformationDeletePopupComponent
    ],
    entryComponents: [
        ExperienceInformationComponent,
        ExperienceInformationUpdateComponent,
        ExperienceInformationDeleteDialogComponent,
        ExperienceInformationDeletePopupComponent
    ],
    exports: [
        ExperienceInformationComponent,
        ExperienceInformationDetailComponent,
        ExperienceInformationUpdateComponent,
        ExperienceInformationDeleteDialogComponent,
        ExperienceInformationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiExperienceInformationModule {}

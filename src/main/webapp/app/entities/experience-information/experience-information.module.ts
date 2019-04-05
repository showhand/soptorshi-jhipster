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

const ENTITY_STATES = [...experienceInformationRoute, ...experienceInformationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
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
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiExperienceInformationModule {}

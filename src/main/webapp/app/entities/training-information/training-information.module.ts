import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    TrainingInformationComponent,
    TrainingInformationDetailComponent,
    TrainingInformationUpdateComponent,
    TrainingInformationDeletePopupComponent,
    TrainingInformationDeleteDialogComponent,
    trainingInformationRoute,
    trainingInformationPopupRoute
} from './';

const ENTITY_STATES = [...trainingInformationRoute, ...trainingInformationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TrainingInformationComponent,
        TrainingInformationDetailComponent,
        TrainingInformationUpdateComponent,
        TrainingInformationDeleteDialogComponent,
        TrainingInformationDeletePopupComponent
    ],
    entryComponents: [
        TrainingInformationComponent,
        TrainingInformationUpdateComponent,
        TrainingInformationDeleteDialogComponent,
        TrainingInformationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiTrainingInformationModule {}

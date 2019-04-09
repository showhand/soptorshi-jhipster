import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    TrainingInformationAttachmentComponent,
    TrainingInformationAttachmentDetailComponent,
    TrainingInformationAttachmentUpdateComponent,
    TrainingInformationAttachmentDeletePopupComponent,
    TrainingInformationAttachmentDeleteDialogComponent,
    trainingInformationAttachmentRoute,
    trainingInformationAttachmentPopupRoute
} from './';

const ENTITY_STATES = [...trainingInformationAttachmentRoute, ...trainingInformationAttachmentPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TrainingInformationAttachmentComponent,
        TrainingInformationAttachmentDetailComponent,
        TrainingInformationAttachmentUpdateComponent,
        TrainingInformationAttachmentDeleteDialogComponent,
        TrainingInformationAttachmentDeletePopupComponent
    ],
    entryComponents: [
        TrainingInformationAttachmentComponent,
        TrainingInformationAttachmentUpdateComponent,
        TrainingInformationAttachmentDeleteDialogComponent,
        TrainingInformationAttachmentDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiTrainingInformationAttachmentModule {}

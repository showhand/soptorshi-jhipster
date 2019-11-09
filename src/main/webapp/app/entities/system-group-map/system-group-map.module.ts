import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SystemGroupMapComponent,
    SystemGroupMapDeleteDialogComponent,
    SystemGroupMapDeletePopupComponent,
    systemGroupMapPopupRoute,
    systemGroupMapRoute,
    SystemGroupMapUpdateComponent
} from './';

const ENTITY_STATES = [...systemGroupMapRoute, ...systemGroupMapPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [],
    entryComponents: [
        SystemGroupMapComponent,
        SystemGroupMapUpdateComponent,
        SystemGroupMapDeleteDialogComponent,
        SystemGroupMapDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSystemGroupMapModule {}

import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    InventoryLocationDeleteDialogExtendedComponent,
    InventoryLocationDeletePopupComponentExtended,
    InventoryLocationDetailExtendedComponent,
    InventoryLocationExtendedComponent,
    inventoryLocationExtendedRoute,
    inventoryLocationPopupRouteExtended,
    InventoryLocationUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...inventoryLocationExtendedRoute, ...inventoryLocationPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InventoryLocationExtendedComponent,
        InventoryLocationDetailExtendedComponent,
        InventoryLocationUpdateExtendedComponent,
        InventoryLocationDeleteDialogExtendedComponent,
        InventoryLocationDeletePopupComponentExtended
    ],
    entryComponents: [
        InventoryLocationExtendedComponent,
        InventoryLocationUpdateExtendedComponent,
        InventoryLocationDeleteDialogExtendedComponent,
        InventoryLocationDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiInventoryLocationModuleExtended {}

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IVendor, Vendor } from 'app/shared/model/vendor.model';
import { VendorDeletePopupComponent, VendorDetailComponent, VendorResolve, VendorService } from 'app/entities/vendor';
import { VendorExtendedComponent } from 'app/entities/vendor-extended/vendor-extended.component';
import { VendorExtendedDetailComponent } from 'app/entities/vendor-extended/vendor-extended-detail.component';
import { VendorExtendedUpdateComponent } from 'app/entities/vendor-extended/vendor-extended-update.component';
import { VendorExtendedUpdatedPopupComponent } from 'app/entities/vendor-extended/vendor-extended-update-dialog.component';

@Injectable({ providedIn: 'root' })
export class VendorExtendedResolve {
    constructor(public service: VendorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVendor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Vendor>) => response.ok),
                map((vendor: HttpResponse<Vendor>) => vendor.body)
            );
        }
        return of(new Vendor());
    }
}

export const vendorExtendedRoute: Routes = [
    {
        path: '',
        component: VendorExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            defaultSort: 'id,desc',
            pageTitle: 'Vendors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VendorExtendedDetailComponent,
        resolve: {
            vendor: VendorExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'Vendors',
            breadcrumb: 'Vendor Details'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VendorExtendedUpdateComponent,
        resolve: {
            vendor: VendorExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'Vendors',
            breadcrumb: 'New Vendor'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VendorExtendedUpdateComponent,
        resolve: {
            vendor: VendorExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'Vendors',
            breadcrumb: 'Edit Vendor'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vendorExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VendorDeletePopupComponent,
        resolve: {
            vendor: VendorExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'Vendors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'add-new-popup',
        component: VendorExtendedUpdatedPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'Create Vendor'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

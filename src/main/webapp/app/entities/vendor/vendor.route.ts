import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vendor } from 'app/shared/model/vendor.model';
import { VendorService } from './vendor.service';
import { VendorComponent } from './vendor.component';
import { VendorDetailComponent } from './vendor-detail.component';
import { VendorUpdateComponent } from './vendor-update.component';
import { VendorDeletePopupComponent } from './vendor-delete-dialog.component';
import { IVendor } from 'app/shared/model/vendor.model';

@Injectable({ providedIn: 'root' })
export class VendorResolve implements Resolve<IVendor> {
    constructor(private service: VendorService) {}

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

export const vendorRoute: Routes = [
    {
        path: '',
        component: VendorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Vendors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VendorDetailComponent,
        resolve: {
            vendor: VendorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vendors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VendorUpdateComponent,
        resolve: {
            vendor: VendorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vendors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VendorUpdateComponent,
        resolve: {
            vendor: VendorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vendors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vendorPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VendorDeletePopupComponent,
        resolve: {
            vendor: VendorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vendors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

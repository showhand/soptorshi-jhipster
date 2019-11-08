/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyShopDetailComponent } from 'app/entities/supply-shop/supply-shop-detail.component';
import { SupplyShop } from 'app/shared/model/supply-shop.model';

describe('Component Tests', () => {
    describe('SupplyShop Management Detail Component', () => {
        let comp: SupplyShopDetailComponent;
        let fixture: ComponentFixture<SupplyShopDetailComponent>;
        const route = ({ data: of({ supplyShop: new SupplyShop(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyShopDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyShopDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyShopDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyShop).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

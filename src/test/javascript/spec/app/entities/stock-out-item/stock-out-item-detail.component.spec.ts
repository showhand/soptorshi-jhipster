/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { StockOutItemDetailComponent } from 'app/entities/stock-out-item/stock-out-item-detail.component';
import { StockOutItem } from 'app/shared/model/stock-out-item.model';

describe('Component Tests', () => {
    describe('StockOutItem Management Detail Component', () => {
        let comp: StockOutItemDetailComponent;
        let fixture: ComponentFixture<StockOutItemDetailComponent>;
        const route = ({ data: of({ stockOutItem: new StockOutItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [StockOutItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StockOutItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockOutItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stockOutItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

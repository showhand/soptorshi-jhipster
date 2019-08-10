/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { StockInItemDetailComponent } from 'app/entities/stock-in-item/stock-in-item-detail.component';
import { StockInItem } from 'app/shared/model/stock-in-item.model';

describe('Component Tests', () => {
    describe('StockInItem Management Detail Component', () => {
        let comp: StockInItemDetailComponent;
        let fixture: ComponentFixture<StockInItemDetailComponent>;
        const route = ({ data: of({ stockInItem: new StockInItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [StockInItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StockInItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockInItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stockInItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

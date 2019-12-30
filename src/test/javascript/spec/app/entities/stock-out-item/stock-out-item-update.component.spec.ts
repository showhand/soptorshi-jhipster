/* tslint:disable max-line-length */
import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {HttpResponse} from '@angular/common/http';
import {of} from 'rxjs';

import {SoptorshiTestModule} from '../../../test.module';
import {StockOutItemUpdateComponent} from 'app/entities/stock-out-item/stock-out-item-update.component';
import {StockOutItemService} from 'app/entities/stock-out-item/stock-out-item.service';
import {StockOutItem} from 'app/shared/model/stock-out-item.model';

describe('Component Tests', () => {
    describe('StockOutItem Management Update Component', () => {
        let comp: StockOutItemUpdateComponent;
        let fixture: ComponentFixture<StockOutItemUpdateComponent>;
        let service: StockOutItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [StockOutItemUpdateComponent]
            })
                .overrideTemplate(StockOutItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockOutItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockOutItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StockOutItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stockOutItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StockOutItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stockOutItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

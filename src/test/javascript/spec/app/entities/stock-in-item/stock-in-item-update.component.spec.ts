/* tslint:disable max-line-length */
import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {HttpResponse} from '@angular/common/http';
import {of} from 'rxjs';

import {SoptorshiTestModule} from '../../../test.module';
import {StockInItemUpdateComponent} from 'app/entities/stock-in-item/stock-in-item-update.component';
import {StockInItemService} from 'app/entities/stock-in-item/stock-in-item.service';
import {StockInItem} from 'app/shared/model/stock-in-item.model';

describe('Component Tests', () => {
    describe('StockInItem Management Update Component', () => {
        let comp: StockInItemUpdateComponent;
        let fixture: ComponentFixture<StockInItemUpdateComponent>;
        let service: StockInItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [StockInItemUpdateComponent]
            })
                .overrideTemplate(StockInItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockInItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockInItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StockInItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stockInItem = entity;
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
                    const entity = new StockInItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stockInItem = entity;
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
